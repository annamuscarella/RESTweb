package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity// also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class TopPlayer {

  private String name;
  private int score;
  private String nation;


  public TopPlayer(String name, int score, String nation){
    this.name=name;
    this.score = score;
    this.nation=nation;
  }

  public String getNation(){return nation;}
  public void setNation(String nation){this.nation = nation;}
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }


  public String toString(){
      String s = "Score from " + name + " is: " + score;
      return s;
  }



}
