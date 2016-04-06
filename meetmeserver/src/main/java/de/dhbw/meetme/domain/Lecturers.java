package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class Lecturers extends PersistentObject {

  private String LecturerFirstname;
  private String LecturerLastname;
  private String LecturerTopic;
  private boolean LecturerAvailability;

  public String getLecturerFirstname() {
    return LecturerFirstname;
  }

  public void setLecturerFirstname(String lecturerFirstname) {
    LecturerFirstname = lecturerFirstname;
  }

  public String getLecturerLastname() {
    return LecturerLastname;
  }

  public void setLecturerLastname(String lecturerLastname) {
    LecturerLastname = lecturerLastname;
  }

  public String getLecturerTopic() {
    return LecturerTopic;
  }

  public void setLecturerTopic(String lecturerTopic) {
    LecturerTopic = lecturerTopic;
  }

  public boolean isLecturerAvailability() {
    return LecturerAvailability;
  }

  public void setLecturerAvailability(boolean lecturerAvailability) {
    LecturerAvailability = lecturerAvailability;
  }



  public Lecturers(String LecturerFirstname, String LecturerLastname, String LecturerTopic){
    this.LecturerFirstname=LecturerFirstname;
    this.LecturerLastname=LecturerLastname;
    this.LecturerTopic=LecturerTopic;
  }

}