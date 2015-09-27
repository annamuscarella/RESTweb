package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.dao.UserDao;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;

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

    /*@PUT
    @Path("/{userName}/{gpsData}")
    // schickt die aktuelle GPS Position und speichert sie in der DB
    public void updateGps (@PathParam("userName") String userName,@PathParam("gpsData") String gpsData)
    {
        if (userDao.existCheckName(userName)== true)
        {
            log.debug(userName + " hat seine GPS Daten aktualisiert");
            userDao.updateGPS(userName, gpsData);
            return;
        }
        log.debug("Jemand hat versucht seine GPS Daten aktualisiert aber userName war nicht in der DB");
        return;
    }

    @GET
    @Path("/{userName}")
    // Anfrage der eigenen GPS Koordianten...später werden die Koordinaten von allen Mitspielern geschickt
    public String getGps (@PathParam("userName")String userName)
    {
        if (userDao.existCheckName(userName)== true)
        {
            log.debug(userName + " hat seine GPS Daten angefragt");
            return userDao.getGPS(userName);
        }
        log.debug("Jemand hat versucht seine GPS Daten anzufragen aber userName war nicht in der DB");
        return "Leider bist du nicht angemeldet";
    }*/
}