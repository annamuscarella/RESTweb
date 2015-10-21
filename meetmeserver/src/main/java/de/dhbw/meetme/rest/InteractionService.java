package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.FriendshipDao;
import de.dhbw.meetme.database.dao.GPSDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.Friendship;
import de.dhbw.meetme.domain.GPSLocation;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UserPosition;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

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
    @Inject
    GPSDao gpsDao;
    @Inject
    FriendshipDao friendshipDao;
    @Inject
    Transaction transaction;

    @GET
    @Path("/{username1}/{username2}/{verificationCode}")
    public String meetOtherUser(@PathParam("username1") String username1, @PathParam("username2") String username2, @PathParam("verificationCode") int verificationCode){
        transaction.begin();
        log.debug("testtest");
        User user1 = userDao.findByUserName(username1);
        GPSLocation user1GPS = gpsDao.listLatestGPSByUserSingle(username1);
        User user2 = userDao.findByUserName(username2);
        GPSLocation user2GPS = gpsDao.listLatestGPSByUserSingle(username2);
        log.debug("Test");

        if ( user2GPS != null) {
            //check that one cannot meet him/herself
            log.debug(username2 + " is not null");
            if (!user1GPS.equals(user2GPS)) {
                log.debug(username1 + "and" + username2 + " are not the same person");
                if (GPSService.checkDistance(user1GPS.getLatitude(), user1GPS.getLongitude(), user2GPS.getLatitude(), user2GPS.getLongitude()) < 2000) {
                    if (user2.getVerificationCode() == verificationCode) {
                        //list all friendships and check whether already friends
                        Collection<Friendship> myFriendships = friendshipDao.findByName(username1);
                        //log.debug(myFriendships);
                        for(Friendship f:myFriendships){
                            if (f.getUsername2().equals(username2)){
                                log.debug(username1 + " and " + username2 + "could not meet because already friends");
                                return "false;" + user1.getScore();
                            }
                        }

                        //add to database that users met!!
                        Friendship myFriendship = new Friendship(username1, username2);
                        friendshipDao.persist(myFriendship);


                        //score
                        int score = user1.getScore();
                        score++;
                        user1.setScore(score);
                        userDao.persist(user1);
                        user1.getScore();
                        transaction.commit();
                        return "true;" + user1.getScore();
                    }
                }
            }
        }
        log.debug(username1 + " and " + username2 + "could not meet");
        return "false;"+user1.getScore();
    }

    @GET
    @Path("/{username}")
    public Collection<Friendship> list(@PathParam("username") String username){
        return friendshipDao.findByName(username);
    }


}