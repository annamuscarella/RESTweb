package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UserPosition;
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

/**
 * Created by mordelt on 24.09.2015.
 * This class handles all GPS-related interactions.
 */
@Path("api/interact")
@Produces({"application/json"}) // mime type
@Singleton
public class InteractionService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDao userDao;

    @GET
    @Path("/{username1}/{username2}/{verificationCode}")
    public boolean meetOtherUser(@PathParam("username1") String username1, @PathParam("username2") String username2, @PathParam("verificationCode") int verificationCode){
        User user1 = userDao.findbyUserName(username1);
        User user2 = userDao.findbyUserName(username2);
        if (user2.getVerificationCode == verificationCode){
            //add to database that users met
            //score
            int score = user1.getScore;
            score++;
            //user1.setScore(score);
            userDao.updateScore(user1, score);

            return true;
        }
        return false;
    }


}