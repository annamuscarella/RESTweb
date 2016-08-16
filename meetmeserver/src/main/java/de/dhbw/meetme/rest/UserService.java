package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 *
 */
@Path("/api/screens")
@Produces({"application/json"}) // mime type
@Singleton
public class UserService {
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  @Inject
  UserDao userDao;
  Transaction transaction;

  @Path("/list")
  @GET
  public Collection<User> list() {
    log.debug("List users");
    return userDao.list();
  }

  @Path("/lightTest")
  @GET
  public String lightTest() {
    //log.debug("Save user " + user);
    return "lightTest";
  }

  @Path("/login")
  @GET

  public String login(@HeaderParam("Authorization") String authPara){

    log.debug("authPara: "+ authPara);
    final String authorization =  authPara; //httpRequest.getHeader("Authorization");
    return "false";

  }



}
