package de.dhbw.meetme.rest;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.FriendshipDao;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.database.dao.UserDao;
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

@Path("/api/ranking")
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
    Transaction transaction;
@GET
@Path("/topplayer)")
//bullshit methode!
    public List<TopPlayer> topPlayerList() {

        ArrayList<TopPlayer> myTopPlayerList = new ArrayList<TopPlayer>();
        Collection<Score> myScoreList = scoreDao.scoreList();

        for(Score myScore:myScoreList)
        {
            for(TopPlayer myTopPlayer:myTopPlayerList){
                if(myScore.getUsername().equals(myTopPlayer.getName())){
                    break;
                }
                else {
                    TopPlayer t = new TopPlayer(myScore.getUsername(),myScore.getScore());
                    myTopPlayerList.add(t);
                }
            }
        }

    return myTopPlayerList;
    }

@GET
@Path("/topplayer1)")
    public Collection<Score> topPlayerList1(){

    return scoreDao.scoreList();
}

@GET
@Path("/friendlist/{username}")
    public List<Friends> friendlist (@PathParam("username")String username){

    ArrayList<Friends> myfriendslist = new ArrayList<>();
    Collection<Friendship> userfriendship = friendshipDao.findByName(username);

    for(Friendship myfriendship:userfriendship){

        String fusername = myfriendship.getUsername2();
        Score s = scoreDao.getScore(fusername);
        int scoreDigtit = s.getScore();
        Friends f = new Friends(fusername,scoreDigtit);
        myfriendslist.add(f);
    }
    return myfriendslist;
}
}


