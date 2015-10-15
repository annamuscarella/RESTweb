package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UserPosition;
import groovy.lang.Singleton;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.lang.Math.*;

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
    Transaction transaction;

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
            activeUser.setLatitude(lat);
            activeUser.setLongitude(lon);

            double currentMillis = System.currentTimeMillis();
            //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            //Date currentDate = new Date(currentMillis);
            activeUser.setLastUpdated(currentMillis);

            userDao.persist(activeUser);

            //get Collection<User> from database, containing all users
            Collection<User> users = userDao.list();
            for(User myUser: users){
                //check that myUser is not activeUser
                if (!myUser.getName().equals(username)) {
                    //check distance between active User and myUser and add to nearbyUsers
                    if (checkDistance(lat, lon, myUser.getLatitude(), myUser.getLongitude()) < 10000){
                        UserPosition myUserPosition = new UserPosition(myUser.getName(), myUser.getLatitude(), myUser.getLongitude(), "grey");
                        nearbyUsers.add(myUserPosition);
                    }
                }
            }
            transaction.commit();
            log.debug(username + " hat seine GPS Daten aktualisiert.");
            return nearbyUsers;
        }
        log.debug("Jemand hat versucht seine GPS Daten zu aktualisieren, aber userName " + username + " war nicht in der DB");
        return nearbyUsers;
    }

    @GET
    @Path("/{userName}")
    // Anfrage der eigenen GPS Koordianten...später werden die Koordinaten von allen Mitspielern geschickt
    /*public String getGps (@PathParam("userName")String userName)
    {
        if (userDao.existCheckName(userName)== true)
        {
            log.debug(userName + " hat seine GPS Daten angefragt");
            return userDao.getGPS(userName);
        }
        log.debug("Jemand hat versucht seine GPS Daten anzufragen aber userName war nicht in der DB");
        return "Leider bist du nicht angemeldet";
    }*/


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



}