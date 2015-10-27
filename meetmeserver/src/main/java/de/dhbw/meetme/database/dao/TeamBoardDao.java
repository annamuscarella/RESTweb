package de.dhbw.meetme.database.dao;


import de.dhbw.meetme.domain.Score;
import de.dhbw.meetme.domain.TeamBoard;
import de.dhbw.meetme.domain.UuidId;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class TeamBoardDao extends JpaDao<UuidId, TeamBoard> {
    public TeamBoardDao() {
        super(TeamBoard.class);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);






    public Collection<TeamBoard> leaderTeamBoard(){
        Query query = entityManager.createQuery("select t from TeamBoard t group by t.nation order by t.scoreTeam desc");

        return (Collection<TeamBoard>) query.getResultList();
    }




    public TeamBoard getTeamBoard(String nation){

        Query query = entityManager.createQuery("select t from TeamBoard t where t.nation=:nation order by t.scoreTeam desc");
        query.setParameter("nation", nation);

        return (TeamBoard) query.getResultList().get(0);
    }
    public Collection<TeamBoard> getTeamBoardCollection (String nation){

        Query query = entityManager.createQuery("select t from TeamBoard t where t.nation=:nation order by t.scoreTeam desc");
        query.setParameter("nation", nation);

        return (Collection<TeamBoard>) query.getResultList();
    }

}
