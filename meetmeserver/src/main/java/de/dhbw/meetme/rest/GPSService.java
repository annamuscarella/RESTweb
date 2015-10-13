package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UserPosition;
import groovy.lang.Singleton;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Collection;

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

    @GET
    @Path("/{username}/{lat}/{lon}")
    // schickt die aktuelle GPS Position und speichert sie in der DB
    public ArrayList<UserPosition> updateGps (@PathParam("username") String username,@PathParam("lat") double lat ,@PathParam("lon") double lon)
    {
        ArrayList<UserPosition> nearbyUsers = new ArrayList<UserPosition>();
        Collection<User> activeUsers = userDao.findByName(username);
        if (activeUsers.size() > 0)
        {
            //send GPS data to database
            userDao.updateGPS(username, lat, lon);

            //get Collection<User> from database, containing all users
            Collection<User> users = userDao.list();
            for(User myUser: users){
                //check that myUser is not activeUser
                if (myUser.getName() != username) {
                    //check distance between active User and myUser and add to nearbyUsers
                    if (checkDistance(lat, lon, myUser.lat, myUser.lon) < 10000){
                        UserPosition myUserPosition = new UserPosition(myUser.getName(), myUser.lat, myUser.lon, "grey");
                        nearbyUsers.add(myUserPosition);
                    }
                }

            }
            log.debug(username + " hat seine GPS Daten aktualisiert");
            return nearbyUsers;
        }
        log.debug("Jemand hat versucht seine GPS Daten zu aktualisieren, aber userName war nicht in der DB");
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