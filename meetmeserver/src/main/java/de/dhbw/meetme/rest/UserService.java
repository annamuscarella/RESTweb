/*package de.dhbw.meetme.rest;

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
import java.util.Base64;
import java.util.Collection;

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
    if (authorization != null && authorization.startsWith("Basic")) {
      // Authorization: Basic base64credentials
      String base64Credentials = authorization.substring("Basic".length()).trim();
      String credentials = new String(Base64.getDecoder().decode(base64Credentials),
              Charset.forName("UTF-8"));
      // credentials = username:password
      final String[] values = credentials.split(":",2);
      log.debug("username "+ values[0]);
      log.debug("password "+ values[1]);
      User s= null;
      try{
       s= userDao.findByUserName(values[0]);}
      catch (Exception e ){
        log.debug("Fehler user gibt es nicht ");
        return "false";
      }
      log.debug("user"+ s);

      if (values[1].equalsIgnoreCase(s.getPassword())){
      return "true";}
      else return "false";
    }
    return "false";

  }



}
*/