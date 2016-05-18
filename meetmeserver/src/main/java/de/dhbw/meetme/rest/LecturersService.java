package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.AppointmentDao;
import de.dhbw.meetme.database.dao.LecturerDao;
import de.dhbw.meetme.domain.Appointment;
import de.dhbw.meetme.domain.Lecturers;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.net.URI;

/**
 *this class handles all the interaction with the lecturer
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
    //tested
    public Collection<Lecturers> listLecturers() {
        log.debug("list all lecturers");
        return lecturerDao.list();
    }



    @GET
    @Path("/{lecturerName}")
    // returns the DB entry of one single lecturer
    //tested
    public Lecturers findLecturer(@PathParam("lecturerName") String lecturerName){
        log.debug("list lecturer"+ lecturerName);

        return lecturerDao.findLecturer(lecturerName);
    }

    @POST
    @Path("/save")
    //to add new lecturers to the DB
    //tested
        public String save(@FormParam("lecturerFirstname") String lecturerFirstname, @FormParam("lecturerLastname") String lecturerLastname, @FormParam("lecturerTopic") String lecturerTopic,@FormParam("lecturerMail")String lecturerMail, @FormParam("lecturerPw")String lecturerPw) {
        transaction.begin();
        Lecturers lecturer = new Lecturers(lecturerFirstname, lecturerLastname, lecturerTopic,lecturerMail,lecturerPw, true);

        lecturerDao.persist(lecturer);
        log.debug("Save lecturer " + lecturer);
        transaction.commit();
        return "save lecturer";
    }

    @POST
    @Path("/appointment")
    //to schedule an appointment in advance
    //tested
        public Response appointment(@FormParam("advisor") String lecturerName, @FormParam("name") String studentFName,@FormParam("surname") String studentLName, @FormParam("email") String studentMail, @FormParam("topic") String topic, @FormParam("date") String date, @FormParam("time") String proposedTime,@FormParam("course") String course){
        transaction.begin();
        if (course.equals("") || course == null){
            course = "N/A";
        }
        Appointment appointment = new Appointment(lecturerName, date, proposedTime, studentFName,studentLName, studentMail,course,topic);
        appointmentDao.persist(appointment);
        log.debug("Save appoinment " + appointment);
        transaction.commit();

        URI location = null;
        try {
            location = new java.net.URI("appointmentSuccess.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new WebApplicationException(Response.temporaryRedirect(location).build());
        }

    @GET
    @Path("/appointment/{lecturerName}")
    //provide all requests for scheduled appointments
    //tested
    public Collection<Appointment> listLecturers(@PathParam("lecturerName") String lecturerName) {
        log.debug("list all appointments of "+lecturerName);
        return appointmentDao.getLecturerAppointment(lecturerName);
    }

    @GET
    @Path("/availability/{lecturerName}")
    //to fetch the availability of the lecturer
    //tested
        public boolean availability(@PathParam("lecturerName") String lecturerName){
        Lecturers lecturer = lecturerDao.findLecturer(lecturerName);
        return lecturer.isLecturerAvailability();
    }
    @POST
    @Path("/availability/{setAvailability}/{lecturerName}")
    //set the availability ...boolean true for availabale or false for unavailable
    //tested
    public boolean setAvailability(@PathParam("setAvailability") boolean availability,@PathParam("lecturerName") String lecturerName){
        transaction.begin();
        Lecturers lecturer = lecturerDao.findLecturer(lecturerName);
        lecturer.setLecturerAvailability(availability);


        log.debug("availability set to: " + availability);
        transaction.commit();


        return true;
    }
    @GET
    @Path("/login")
    //verify the lecturer by checking mail and password
    public Response login(@FormParam("email") String mail, @FormParam("password") String pw){
        URI location = null;
        Lecturers lecturer = null;

        lecturer = lecturerDao.findLecturerMail(mail);

        log.debug("lecturer find by mail: "+lecturerDao.findLecturerMail(mail));
        log.debug("pw von lect: "+lecturerDao.findLecturerMail(mail).getLecturerPw());
        if (lecturer.getLecturerPw().equals(pw)){
            try {
                location = new java.net.URI("advisorInterface.html");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                location = new java.net.URI("loginPage.html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        throw new WebApplicationException(Response.temporaryRedirect(location).build());
    }
}
