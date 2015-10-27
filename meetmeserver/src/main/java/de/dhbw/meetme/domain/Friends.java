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
 // private int score;
  private String nation;




  public Friends(String username, String nation){
    this.username=username;
    this.nation=nation;

  }

 /* public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
*/
  public String getUsername() {
    return username;
  }

  public void setUsername(String Username) {
    this.username = username;
  }

public String getNation (){return nation;
}
  public void setNation(String nation){this.nation=nation;}

  public String toString(){
      String s = username;
      return s;
  }



}
