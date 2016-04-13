package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.FriendshipDao;
import de.dhbw.meetme.database.dao.GPSDao;
import de.dhbw.meetme.database.dao.LecturerDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.Lecturers;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UserPosition;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by mordelt on 24.09.2015.
 * This class handles all GPS-related interactions.
 */
@Path("api/lecturer")
@Produces({"application/json"}) // mime type
@Singleton
public class LecturersService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    LecturerDao lecturerDao;


    @Inject
    Transaction transaction;

    @GET
    @Path("/list")
    //returns list of all lecturers saved in the database
    public Collection<Lecturers> listLecturers() {
        log.debug("list all lecturers");
        return lecturerDao.list();
    }



    @GET
    @Path("/{lecturerName}")
    // returns the DB entry of one single lecturer
    public Lecturers findLecturer(@PathParam("lecturerName") String lecturerName){
        log.debug("list lecturer"+ lecturerName);

        return lecturerDao.findLecturer(lecturerName);
    }

    @Path("/save")
    @POST
    public String save(@FormParam("lecturerFirstname") String lecturerFirstname, @FormParam("lecturerLastname") String lecturerLastname, @FormParam("lecturerTopic") String lecturerTopic) {
        Lecturers lecturer = new Lecturers(lecturerFirstname, lecturerLastname, lecturerTopic);

        lecturerDao.persist(lecturer);
        log.debug("Save lecturer " + lecturer);
        return "save lecturer";
    }


}
