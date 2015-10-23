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
  private int score;

  public UuidId getid(){
    return id;
  }
  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

public Score(String username, int score){
  this.username = username;
  this.score = score;
}






}
