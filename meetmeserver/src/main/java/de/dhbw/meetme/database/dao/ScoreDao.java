package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.Score;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.ArrayList;
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
public class ScoreDao extends JpaDao<UuidId, Score> {
    public ScoreDao() {
        super(Score.class);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);




  /* public ArrayList<Score> listUniqueLatestGPS(){
        //this method returns a collection of the latest GPS location, for each user only once location
        Collection<String> myUsers = listUsersinGPS();
        ArrayList<GPSLocation> myGPSLocations = new ArrayList<>();
        for(String username:myUsers){
            //for each user in myUsers, get the latest GPSData and append
            myGPSLocations.addAll(listLatestGPSByUser(username));
        }
        return Score;
    }*/


    public Collection<Score> scoreList(){
        Query query = entityManager.createQuery("select score from Score score group by score.username order by score.score desc");

        return (Collection<Score>) query.getResultList();
    }

    public Collection<Score> listgetScore(String name){
        Query query = entityManager.createQuery("select score from Score score where score.username=:name order by score.score desc");
        query.setParameter("name", name);

        return (Collection<Score>) query.setMaxResults(1).getResultList();
    }


    public Score getScore(String name){
        Query query = entityManager.createQuery("select score from Score score where score.username=:name order by score.score desc");
        query.setParameter("name", name);
        return (Score) query.getResultList().get(0);
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
