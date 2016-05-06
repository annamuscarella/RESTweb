package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.AppointmentDao;
import de.dhbw.meetme.database.dao.LecturerDao;
import de.dhbw.meetme.domain.Appointment;
import de.dhbw.meetme.domain.Lecturers;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.*;

/**
 *
 */
@Path("api/lecturer")
@Produces({"application/json"}) // mime type
@Singleton
public class LecturersService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    LecturerDao lecturerDao;
    @Inject
    AppointmentDao appointmentDao;

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

    @POST
    @Path("/save")
        public String save(@FormParam("lecturerFirstname") String lecturerFirstname, @FormParam("lecturerLastname") String lecturerLastname, @FormParam("lecturerTopic") String lecturerTopic) {
        Lecturers lecturer = new Lecturers(lecturerFirstname, lecturerLastname, lecturerTopic, true);

        lecturerDao.persist(lecturer);
        log.debug("Save lecturer " + lecturer);
        return "save lecturer";
    }

    @POST
    @Path("/appointment")
        public String appointment(@FormParam("lecturerName") String lecturerName, @FormParam("studentName") String studentName, @FormParam("studentMail") String studentMail, @FormParam("tobic") String tobic, @FormParam("date") String date, @FormParam("proposedTime") String proposedTime){
        Appointment appointment = new Appointment(lecturerName, date, proposedTime, studentName, studentMail);
        appointmentDao.persist(appointment);
        log.debug("Save appoinment " + appointment);
        return "save appointment";
        }

    @GET
    @Path("/availability/{lecturerName}")
        public boolean availability(@PathParam("lecturerName") String lecturerName){
        Lecturers lecturer = lecturerDao.findLecturer(lecturerName);
        return lecturer.isLecturerAvailability();
    }
}