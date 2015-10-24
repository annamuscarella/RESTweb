package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class TeamBoard extends PersistentObject {
  private String nation;
  private int scoreTeam;
  private int playerCounter;

  public int getPlayerCounter() {
    return playerCounter;
  }

  public void setPlayerCounter(int playerCounter) {
    this.playerCounter = playerCounter;
  }

  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }

  public int getScoreTeam() {
    return scoreTeam;
  }

  public void setScoreTeam(int scoreTeam) {
    this.scoreTeam = scoreTeam;
  }

  public void updateTeamScore(int points){
    int p = getScoreTeam();
    p=p+points;
    setScoreTeam(p);
    return;
  }

  public void updatePlayerCounter(){
    playerCounter=playerCounter+1;
    return;
  }

  public TeamBoard (String nation){
    this.nation = nation;
    scoreTeam= 0;
    playerCounter=0;
  }

}







