package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class Score extends PersistentObject {
  private String username;
  private int scoreNb;

  public UuidId getid(){
    return id;
  }
  public int getScoreNb() {
    return scoreNb;
  }

  public void setScoreNb(int scoreNb) {
    this.scoreNb = scoreNb;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

public Score(String username, int scoreNb){
  this.username = username;
  this.scoreNb = scoreNb;
}






}
