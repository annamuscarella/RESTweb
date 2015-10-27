package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity// also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class TopTeamList {

    private String nation;
    private int score;

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public TopTeamList(String nation,  int score){
    this.nation=nation;
    this.score=score;

  }





}
