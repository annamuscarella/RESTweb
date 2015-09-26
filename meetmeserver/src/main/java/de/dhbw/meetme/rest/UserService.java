package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Path("/api/user")
@Produces({"application/json"}) // mime type
@Singleton
public class UserService {
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  @Inject
  UserDao userDao;

  @Path("/list")
  @GET
  public Collection<User> list() {
    log.debug("List users");
    return userDao.list();
  }

  @Path("/get/{id}")
  @GET
  public User get(@PathParam("id") String id) {
    log.debug("Get user " + id);
    return userDao.get(UuidId.fromString(id));
  }

  @Path("/delete/{id}")
  @DELETE
  public void delete(@PathParam("id") String id) {
    log.debug("Delete user " + id);
    userDao.delete(UuidId.fromString(id));
  }

  @Path("/save")
  @POST
  public void save(@FormParam("regEmail") String regEmail, @FormParam("userName") String userName, @FormParam("registerPassword") String registerPassword, @PathParam("nationality") String nationality){

    if( registerPassword == "" | regEmail == "" | userName == "" ){
      log.debug(userName + "tried to register but not all forms were filled");
      // Meldung an Browser bzw. App das nicht alles ausgefüllt worden ist
      // Registrierung abbrechen
    }

    //userDao.persist(regEmail, userName, registerPassword);
    //userDao.persist(user);
    //log.debug("Save user " + user);
  }

}
