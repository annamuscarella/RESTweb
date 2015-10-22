package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.GPSLocation;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Data Access Object for User
 * <p>
 * This class is responsible to do the CRUD (create, read, update, delete) operations
 * for user objects in the database.
 * <p>
 * There is an alternative implementation UserClassicDao using the traditional way.
 * <p>
 * Decide yourself which one you want to use.
 * You may even mix both approaches.
 */
@ApplicationScoped
public class GPSDao extends JpaDao<UuidId, GPSLocation> {
    public GPSDao() {
        super(GPSLocation.class);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    public Collection<GPSLocation> findByName(String name) {
        Query query = entityManager.createQuery("select gps from GPSLocation gps where gps.username = :name");
        query.setParameter("name", name);
        return (Collection<GPSLocation>) query.getResultList();
    }

    public ArrayList<GPSLocation> listUniqueLatestGPS(){
        //this method returns a collection of the latest GPS location, for each user only once location
        Collection<String> myUsers = listUsersinGPS();
        ArrayList<GPSLocation> myGPSLocations = new ArrayList<>();
        for(String username:myUsers){
            //for each user in myUsers, get the latest GPSData and append
            myGPSLocations.addAll(listLatestGPSByUser(username));
        }
        return myGPSLocations;
    }

    public Collection<String> listUsersinGPS(){
        Query query = entityManager.createQuery("select gps.username from GPSLocation gps group by gps.username");
        return (Collection<String>) query.getResultList();
    }

    public Collection<GPSLocation> listLatestGPSByUser(String name){
        Query query = entityManager.createQuery("select gps from GPSLocation gps where gps.username=:name order by gps.timeStamp desc");
        query.setParameter("name", name);
        return (Collection<GPSLocation>) query.setMaxResults(1).getResultList();
    }

    //TODO This method does not work yet, returning only one GPSLocation is a problem (Return Collection?)
    public GPSLocation listLatestGPSByUserSingle(String name){
        Query query = entityManager.createQuery("select gps from GPSLocation gps where gps.username=:name order by gps.timeStamp desc");
        query.setParameter("name", name);
        return (GPSLocation) query.getResultList().get(0);
    }

    /*public Collection<User> findByEmail(String email) {
        Query query = entityManager.createQuery("select u from User u where u.email = :email");
        query.setParameter("email", email);
        return (Collection<User>) query.getResultList();
    }

    public User findByUserName(String name) {
        Query query = entityManager.createQuery("SELECT u from User u where u.name = :name"); //prev.: from User u where u.username = :username"
        query.setParameter("name", name);
        return (User) query.getResultList().get(0);
    }*/
}
