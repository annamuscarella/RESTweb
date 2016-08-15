package de.dhbw.meetme.rest;

import com.sun.xml.internal.bind.v2.TODO;
import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.AppReplyDao;
import de.dhbw.meetme.database.dao.AppointmentDao;
import de.dhbw.meetme.database.dao.LecturerDao;
import de.dhbw.meetme.database.dao.UrgentAppointmentDao;
import de.dhbw.meetme.domain.AppReply;
import de.dhbw.meetme.domain.Appointment;
import de.dhbw.meetme.domain.Lecturers;
import de.dhbw.meetme.domain.UrgentAppointment;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;

/**
 *
 */
@Path("api/lights")
@Produces({"application/json"}) // mime type
@Singleton
public class LightService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    LecturerDao lecturerDao;
    @Path("/list")
    @GET
    public String list() {
        //log.debug("Save user " + user);
        return "lightTest";
    }

    @Path("/lightTest")
    @GET
    public String lightTest() {
        //log.debug("Save user " + user);
        return "lightTest";
    }

    @Path("/getState/{id}")
    @GET
    public String getState(@PathParam("id") Integer id) {
        log.debug("Get user " + id);
        return "You requested the current State for light " + id.toString();
    }


    @Path("/setPower/{id}/{state}")
    @POST
    public String setPower(@PathParam("id") Integer id, @PathParam("state") String state) {
        return ("You set the power state for light " + id.toString() + "to " + state);
    }

    @Path("/setHue/{id}/{value}")
    @POST
    public String setHue(@PathParam("id") Integer id, @PathParam("value") Integer value) {
        return ("You set the Hue for light " + id.toString() + "to " + value.toString());
    }

    @Path("/setSaturation/{id}/{value}")
    @POST
    public String setSaturation(@PathParam("id") Integer id, @PathParam("value") Integer value) {
        return ("You set the Saturation value for light " + id.toString() + "to " + value.toString());
    }

    @Path("/setBrightness/{id}/{value}")
    @POST
    public String setBrightness(@PathParam("id") Integer id, @PathParam("value") Integer value) {
        return ("You set the Brightness value for light " + id.toString() + "to " + value.toString());
    }


}

