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
public class ScoreDao extends JpaDao<UuidId,Score> {
    public ScoreDao() {
        super(Score.class);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);






    public Collection<Score> scoreList(){
        Query query = entityManager.createQuery("select score from Score score group by score.username order by score.scoreNb desc");

        return (Collection<Score>) query.setMaxResults(5).getResultList();
    }

    public Collection<Score> listgetScore(String name){
        Query query = entityManager.createQuery("select score from Score score where score.username=:name order by score.scoreNb desc");
        query.setParameter("name", name);

        return (Collection<Score>) query.setMaxResults(1).getResultList();
    }


    public Score getScore(String name){
        log.debug("getString name : "+name);
        Query query = entityManager.createQuery("select score from Score score where score.username=:name order by score.scoreNb desc");
        query.setParameter("name", name);
        log.debug("Score to String: "+ query.getResultList().get(0).toString());
        return (Score) query.getResultList().get(0);
    }


}
