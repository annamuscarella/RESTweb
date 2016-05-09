package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.*;
import de.dhbw.meetme.domain.*;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by B-Ron on 24.09.2015.
 * This class handles all GPS-related interactions.
 */
@Path("api/admin")
@Produces({"application/json"}) // mime type
@Singleton
public class YoloAdmin {
   //bullshit class
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDao userDao;
    @Inject
    LecturerDao lecturerDao;
    @Inject
    ScoreDao scoreDao;
    @Inject
    UrgentAppointmentDao urgentAppointmentDao;
    @Inject
    AppointmentDao appointmentDao;

    @Inject
    Transaction transaction;
/*
    @GET
    @Path("/setNations")
    //returns list of all GPS Locations saved in the database
    public String setNations(){
            transaction.begin();
        if(teamBoardDao.leaderTeamBoard().isEmpty()){
            TeamBoard d = new TeamBoard("german");
            TeamBoard n = new TeamBoard("notGerman");
            teamBoardDao.persist(d);
            teamBoardDao.persist(n);
            transaction.commit();
            return "german und notGerman wurden erstellt";
        }
        transaction.commit();
        return "es sind bereits Nationen in der Datenbank";

    }
*/
    @GET
    @Path("/test/{Fname}/{Lname}/{topic}/{lecturerMail}/{lecturerPw}")
    public Lecturers getTeamboard(@PathParam("Fname")String Fname, @PathParam("Lname") String Lname,@PathParam("topic") String topic,@PathParam("lecturerMail") String lecturerMail,@PathParam("lecturerPw") String lecturerPw ){
        transaction.begin();
        Lecturers lecturer = new Lecturers(Fname,Lname, topic,lecturerMail,lecturerPw, true);
        lecturerDao.persist(lecturer);
        transaction.commit();

        return lecturer;
    }

    @GET
    @Path("/login/{email}/{password}")
    //verify the lecturer by checking mail and password
    public boolean login(@PathParam("email") String mail, @PathParam("password") String pw){
        Lecturers lecturer = lecturerDao.findLecturerMail(mail);
        if (lecturer.getLecturerPw().equals(pw)){
            return true;
        }
        else return false;

    }
}