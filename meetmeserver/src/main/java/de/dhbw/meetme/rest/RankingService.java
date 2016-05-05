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
/**
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


                    TopPlayer t = new TopPlayer(myScore.getUsername(),myScore.getScoreNb(),userDao.findByUserName(myScore.getUsername()).getNation());
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

    String nation;
    User s;
   for(Friendship myfriendship:userfriendship){


       fusername = myfriendship.getUsername2();
       log.debug("test3: "+fusername);
       s = userDao.findByUserName(username);
       log.debug("test4: "+s.toString());
       nation = s.getNation();
       log.debug("test5: "+nation);
       Friends f = new Friends(fusername,nation);
        myfriendslist.add(f);
    }
    return myfriendslist;

}

@GET
@Path("/teamleaderboard")
//return the TeamLeaderBoard with String nation, int scoreTeam, int playerCounter
    public List<TopTeamList> getTeamleaderBoard(){

    List<TopTeamList> myTopTeamList = new ArrayList<TopTeamList>();
    Collection<TeamBoard> teamBoardlist =teamBoardDao.leaderTeamBoard();
    String nation;
    int score;
    for(TeamBoard myteamboardlist:teamBoardlist){


        nation = myteamboardlist.getNation();

        score = myteamboardlist.getScoreTeam();

        TopTeamList f = new TopTeamList(nation,score);
        myTopTeamList.add(f);
    }


    return myTopTeamList;
}
}



*/