package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity// also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class Friends {

  private String username;
  private int score;





  public Friends(String name, int score){
    this.username=username;
    this.score=score;

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

  public void setUsername(String Username) {
    this.username = username;
  }



  public String toString(){
      String s = username;
      return s;
  }



}
