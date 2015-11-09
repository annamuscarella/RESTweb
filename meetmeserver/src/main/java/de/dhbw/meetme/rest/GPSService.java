package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.FriendshipDao;
import de.dhbw.meetme.database.dao.GPSDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.Friendship;
import de.dhbw.meetme.domain.GPSLocation;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UserPosition;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mordelt on 24.09.2015.
 * This class handles all GPS-related interactions.
 */
@Path("api/gps")
@Produces({"application/json"}) // mime type
@Singleton
public class GPSService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDao userDao;
    @Inject
    GPSDao gpsDao;
    @Inject
    FriendshipDao friendshipDao;

    @Inject
    Transaction transaction;

    @GET
    @Path("/list")
    //returns list of all GPS Locations saved in the database
    public Collection<GPSLocation> listGPSLocations(){
        log.debug("list GPS locations");
        log.debug(gpsDao.list().toString());
        return gpsDao.list();
    }

    @GET
    @Path("/list2")
    public Collection<GPSLocation> listUniqueGPSLocations(){
        log.debug("list unique GPS locations");
        //log.debug(gpsDao.listUniqueLatestGPS().toString());
        return gpsDao.listUniqueLatestGPS();
    }

    @GET
    @Path("/{username}/{lat}/{lon}")
    // schickt die aktuelle GPS Position und speichert sie in der DB
    public List<UserPosition> updateGps (@PathParam("username") String username,@PathParam("lat") double lat ,@PathParam("lon") double lon)
    {
        transaction.begin();

        List<UserPosition> nearbyUsers = new ArrayList<UserPosition>();
        User activeUser = userDao.findByUserName(username);
        if (activeUser != null)
        {
            //send GPS data to database
            GPSLocation myLocation = new GPSLocation(username, lat, lon, System.currentTimeMillis());
            gpsDao.persist(myLocation);
            //activeUser.setLatitude(lat);
            //activeUser.setLongitude(lon);


            //userDao.persist(activeUser);

            //get Collection<User> from database, containing all users
            Collection<User> users = userDao.list();
            Collection<GPSLocation> userPositions = gpsDao.listUniqueLatestGPS();
            for (GPSLocation myGPSLocation: userPositions){
                //check that myUser is not active User
                if (!myGPSLocation.getUsername().equals(username)){
                    //check when use rlast updated his/her position (2 hrs ago)
                    if ((activeUser.getTimeStamp() - myGPSLocation.getTimeStamp() < 7200000)){
                        //check distance between active user and myUser and add to nearbyUsers
                        if (checkDistance(lat, lon, myGPSLocation.getLatitude(), myGPSLocation.getLongitude()) < 10000){
                            String color = checkColor(username, myGPSLocation.getUsername());
                            UserPosition myUserPosition = new UserPosition(myGPSLocation.getUsername(), myGPSLocation.getLatitude(), myGPSLocation.getLongitude(), color);
                            nearbyUsers.add(myUserPosition);

                        }
                    }
                }
            }
            /*for(User myUser: users){
                //check that myUser is not activeUser
                if (!myUser.getName().equals(username)) {
                    //check when user last updated his/her position (2 hrs ago)
                    if ((activeUser.getTimeStamp() - myUser.getTimeStamp() < 7200000)) {
                        //check distance between active User and myUser and add to nearbyUsers
                        if (checkDistance(lat, lon, myUser.getLatitude(), myUser.getLongitude()) < 10000) {
                            UserPosition myUserPosition = new UserPosition(myUser.getName(), myUser.getLatitude(), myUser.getLongitude(), "grey");
                            nearbyUsers.add(myUserPosition);
                        }
                    }
                }
            }*/
            transaction.commit();
            log.debug(username + " hat seine GPS Daten aktualisiert.");
            return nearbyUsers;
        }
        transaction.commit();
        log.debug("Jemand hat versucht seine GPS Daten zu aktualisieren, aber userName " + username + " war nicht in der DB");
        return nearbyUsers;
    }

    @GET
    @Path("/home/{username}")
    public String getHomeLocationFromUser(@PathParam("username") String username){
        Collection<GPSLocation> userLocations = gpsDao.findByName(username);
        List<GPSLocation> userLocationsatNight = new ArrayList<>();
        String time1 = "06:00";
        String time2 = "18:00";
        //check that GPS data was sent between 6pm and 6am
        for(GPSLocation myLocation:userLocations){
            log.debug("time is: " + getTimeHHmm((long)myLocation.getTimeStamp()));
            String myTime = getTimeHHmm((long)myLocation.getTimeStamp());
            int timecheck1 = myTime.compareToIgnoreCase(time1);
            int timecheck2 = myTime.compareToIgnoreCase(time2);
            //if GPS date was sent in reqired time frame the GPS Data will be transfered into new array and longitude and latitude will be cut
            if (timecheck1 >=0 && timecheck2 <=0){
                GPSLocation r = new GPSLocation(username,cutDigit(myLocation.getLatitude()),cutDigit(myLocation.getLongitude()),myLocation.getTimeStamp());
                userLocationsatNight.add(r);
            }

        }
        double finalLongitude= 0;
        double finalLatitude= 0;
        double searchLongitude=0;
        double searchLatitude=0;
        int finalCounter = 0;
        int counter=0;
        for(GPSLocation myCounter:userLocationsatNight){
            if (counter == 0){
                searchLatitude = myCounter.getLatitude();
                searchLongitude= myCounter.getLongitude();
                counter = ++counter;
                continue;
            }
            if(myCounter.getLatitude()== searchLatitude && myCounter.getLongitude() == searchLongitude){
                counter = ++counter;
                continue;
            }
            else{
                if (counter > finalCounter){
                    finalLatitude = searchLatitude;
                    finalLongitude = searchLongitude;
                    finalCounter = counter;
                }
             counter = 1;
             searchLatitude = myCounter.getLatitude();
             searchLongitude = myCounter.getLongitude();
            }

            return finalLatitude +"; "+finalLongitude;
        }
    return null;
    }


    public static double distanceInMeter(double lat1, double lon1, double lat2, double lon2) {
        int earthRadius = 6371000; //meters
        double lat = Math.toRadians(lat2 - lat1);
        double lon = Math.toRadians(lon2- lon1);

        double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lon / 2) * Math.sin(lon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = earthRadius * c;
        return Math.abs(d); // in meter

    }

    public static double checkDistance(double lat1, double lon1, double lat2, double lon2){

        double dist = distanceInMeter(lat1,lon1,lat2,lon2);
        return dist;

    }

    public String checkColor(String username1, String username2){
        //list all friendships in database
        String color;
        Collection<Friendship> myFriendships = friendshipDao.findByName(username1);
        for(Friendship f:myFriendships){
            if (f.getUsername2().equals(username2)){
                User myUser = userDao.findByUserName(username2);
                color = myUser.getColor();
                return color;
            }
        }
        color = "grey";
        return color;
    }

    public String getTimeHHmm(long myTime){
        Instant instant = Instant.ofEpochMilli(myTime);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String output = formatter.format(zdt);

        return output;
    }

    public  double  cutDigit(double digit){
        NumberFormat numberFormat = new DecimalFormat("0.00000");
        numberFormat.setRoundingMode(RoundingMode.DOWN);
        String StringDigit = numberFormat.format(digit);
        StringDigit = StringDigit.replaceAll(",", ".");
        return Double.parseDouble(StringDigit);
    }
}