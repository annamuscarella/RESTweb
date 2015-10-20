package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class Friendship extends PersistentObject {
  private String username1;
  private String username2;

    private int ident;

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

  private static int numberOfFriendships = 1001;
    //TODO after each server start, numberoffriendships is set to 1001 again

  public String getUsername1() {
    return username1;
  }

  public void setUsername1(String username1) {
    this.username1 = username1;
  }

  public String getUsername2() {
    return username2;
  }

  public void setUsername2(String username2) {
    this.username2 = username2;
  }

  public Friendship(String username1, String username2){
    this.username1=username1;
    this.username2=username2;

    this.ident = numberOfFriendships;
    numberOfFriendships++;

  }
}
