package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.dao.UserDao;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

    @POST
    public void sendGPS(){
        //class that sends GPS and user data to database
    }

    @GET
    public void getGPS(){
        //class that returns latest sent GPS position
    }

}
