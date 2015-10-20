package de.dhbw.meetme.database.dao;

import de.dhbw.meetme.domain.GPSLocation;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.domain.UuidId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.Collection;

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


    public Collection<GPSLocation> findByName(String name) {
        Query query = entityManager.createQuery("select gps from GPSLocation gps where gps.username = :name");
        query.setParameter("name", name);
        return (Collection<GPSLocation>) query.getResultList();
    }

    public Collection<GPSLocation> listUniqueLatestGPS(){
        //this method returns a collection of the latest GPS location, for each user only once location
        return null;
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
