package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class Lecturers extends PersistentObject {

  private String lecturerFirstname;
  private String lecturerLastname;
  private String lecturerTopic;
  private boolean lecturerAvailability;
  private String lecturerMail;
  private String lecturerPw;

  public String getLecturerPw() {
    return lecturerPw;
  }

  public void setLecturerPw(String lecturerPw) {
    this.lecturerPw = lecturerPw;
  }

  public String getLecturerMail() {
    return lecturerMail;
  }

  public void setLecturerMail(String lecturerMail) {
    this.lecturerMail = lecturerMail;
  }

  public String getLecturerFirstname() {
    return lecturerFirstname;
  }

  public void setLecturerFirstname(String lecturerFirstname) {
    this.lecturerFirstname = lecturerFirstname;
  }

  public String getLecturerLastname() {
    return lecturerLastname;
  }

  public void setLecturerLastname(String lecturerLastname) {
    this.lecturerLastname = lecturerLastname;
  }

  public String getLecturerTopic() {
    return lecturerTopic;
  }

  public void setLecturerTopic(String lecturerTopic) {
    this.lecturerTopic = lecturerTopic;
  }

  public boolean isLecturerAvailability() {
    return lecturerAvailability;
  }

  public void setLecturerAvailability(boolean lecturerAvailability) {
    this.lecturerAvailability = lecturerAvailability;
  }



  public Lecturers(String lecturerFirstname, String lecturerLastname, String lecturerTopic, String lecturerMail, String lecturerPw, boolean lecturerAvailability){
    this.lecturerFirstname=lecturerFirstname;
    this.lecturerLastname=lecturerLastname;
    this.lecturerTopic=lecturerTopic;
    this.lecturerAvailability = lecturerAvailability;
    this.lecturerMail=lecturerMail;
    this.lecturerPw=lecturerPw;
  }

}