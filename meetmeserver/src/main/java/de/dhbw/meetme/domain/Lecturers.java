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

  public String getLecturerFirstname() {
    return lecturerFirstname;
  }

  public void setLecturerFirstname(String lecturerFirstname) {
    lecturerFirstname = lecturerFirstname;
  }

  public String getLecturerLastname() {
    return lecturerLastname;
  }

  public void setLecturerLastname(String lecturerLastname) {
    lecturerLastname = lecturerLastname;
  }

  public String getLecturerTopic() {
    return lecturerTopic;
  }

  public void setLecturerTopic(String lecturerTopic) {
    lecturerTopic = lecturerTopic;
  }

  public boolean isLecturerAvailability() {
    return lecturerAvailability;
  }

  public void setLecturerAvailability(boolean lecturerAvailability) {
    lecturerAvailability = lecturerAvailability;
  }



  public Lecturers(String lecturerFirstname, String lecturerLastname, String lecturerTopic){
    this.lecturerFirstname=lecturerFirstname;
    this.lecturerLastname=lecturerLastname;
    this.lecturerTopic=lecturerTopic;
  }

}