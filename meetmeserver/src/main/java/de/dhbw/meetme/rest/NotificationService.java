package de.dhbw.meetme.rest;

import com.sun.xml.internal.bind.v2.TODO;
import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.AppointmentDao;
import de.dhbw.meetme.database.dao.LecturerDao;
import de.dhbw.meetme.database.dao.UrgentAppointmentDao;
import de.dhbw.meetme.domain.Appointment;
import de.dhbw.meetme.domain.Lecturers;
import de.dhbw.meetme.domain.UrgentAppointment;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collection;

/**
 *
 */
@Path("api/notification")
@Produces({"application/json"}) // mime type
@Singleton
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    LecturerDao lecturerDao;
    @Inject
    AppointmentDao appointmentDao;
    @Inject
    UrgentAppointmentDao urgentAppointmentDao;

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
    // returns the DB entry of outstanding notifications
    //TODO verfiication wenn mehrere nachrichten ausstehen
    public UrgentAppointment getOpenUrgentAppointments(@PathParam("lecturerName") String lecturerName){
        transaction.begin();
        if (urgentAppointmentDao.getOpenUrgentAppointment(lecturerName)== null)
        {
            log.debug("urgent appointment" + lecturerName+ ": no urgentAppointment");
            transaction.commit();
            return null;

        }
        else {
        //UrgentAppointment myUrgentAppointment =  urgentAppointmentDao.getOpenUrgentAppointment(lecturerName);


        log.debug("urgent appointment" + lecturerName+ " :" +urgentAppointmentDao.getOpenUrgentAppointment(lecturerName));
            urgentAppointmentDao.getOpenUrgentAppointment(lecturerName).setProgressed(true);
            transaction.commit();
            return urgentAppointmentDao.getOpenUrgentAppointment(lecturerName);
    }
/*
    @POST
    @Path("/urgentAppointment")
        public void appReq(@FormParam("lecturerName") String lecturerName, @FormParam("studentFirstname") String studentFirstname,@FormParam("studentLastname") String studentLastname, @FormParam("topic") String topic, @FormParam("course") String course, @FormParam("studentMail") String studentMail) {
        UrgentAppointment urgentAppointment = new UrgentAppointment(lecturerName, studentFirstname + " "+studentLastname, studentMail,course,topic,false);

        urgentAppointmentDao.persist(urgentAppointment);
        log.debug("Save urgentAppointment " + urgentAppointment);

    }

    @POST
    @Path("/appointment")
        public String appointment(@FormParam("lecturerName") String lecturerName, @FormParam("studentName") String studentName, @FormParam("studentMail") String studentMail, @FormParam("topic") String tobic, @FormParam("date") String date, @FormParam("proposedTime") String proposedTime,){
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
    }*/
}}
