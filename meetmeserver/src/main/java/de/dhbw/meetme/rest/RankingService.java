package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.*;
import de.dhbw.meetme.domain.*;
import groovy.lang.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.dhbw.meetme.database.dao.UserDao;

@Path("api/ranking")
@Produces({"application/json"}) // mime type
@Singleton
public class RankingService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDao userDao;
    @Inject
    FriendshipDao friendshipDao;
    @Inject
    ScoreDao scoreDao;
    @Inject
    TeamBoardDao teamBoardDao;
    @Inject
    Transaction transaction;
@GET
@Path("/topplayer")
//doch keine ....die brauchen wir bullshit methode!
    public List<TopPlayer> topPlayerList() {

        ArrayList<TopPlayer> myTopPlayerList = new ArrayList<TopPlayer>();
        Collection<Score> myScoreList = scoreDao.scoreList();

        for(Score myScore:myScoreList)
        {


                    TopPlayer t = new TopPlayer(myScore.getUsername(),myScore.getScoreNb());
                    myTopPlayerList.add(t);


        }

    return myTopPlayerList;
    }

@GET
@Path("/topplayer1")
    public Collection<Score> topPlayerList1(){

    return scoreDao.scoreList();

}

@GET
@Path("/friendlist/{username}")
    public List<Friends> friendlist (@PathParam("username")String username){

    List<Friends> myfriendslist = new ArrayList<Friends>();
    log.debug("test1 "+ myfriendslist.toString());
    Collection<Friendship> userfriendship = friendshipDao.findByName(username);
    log.debug("test2 :"+ userfriendship.toString());
    String fusername;

    int scoreDigit;
    Score s;
   for(Friendship myfriendship:userfriendship){


       fusername = myfriendship.getUsername2();
       log.debug("test3: "+fusername);
       s = scoreDao.getScore(fusername);
       scoreDigit = s.getScoreNb();
       Friends f = new Friends(fusername,scoreDigit);
        myfriendslist.add(f);
    }
    return myfriendslist;

}

@GET
@Path("/teamleaderboard")
//return the TeamLeaderBoard with String nation, int scoreTeam, int playerCounter
    public Collection<TeamBoard> getTeamleaderBoard(){
    return teamBoardDao.leaderTeamBoard();
}
}



