package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class UrgentAppointment extends PersistentObject {

  private String lecturerName;
  private String studentName;
  private String studentMail;
  private String course;
  private String topic;
  private boolean processed;

  public boolean isProgressed() {
    return processed;
  }

  public void setProgressed(boolean processed) {
    this.processed = processed;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }





  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }



  public String getLecturerName() {
    return lecturerName;
  }

  public void setLecturerName(String lecturerName) {
    this.lecturerName = lecturerName;
  }



  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public String getStudentMail() {
    return studentMail;
  }

  public void setStudentMail(String studentMail) {
    this.studentMail = studentMail;
  }


  public UrgentAppointment(String lecturerName, String studentName, String studentMail, String course, String topic, boolean processed) {
    this.lecturerName = lecturerName;
    this.course = course;
    this.studentName = studentName;
    this.studentMail = studentMail;
    this.topic = topic;
    this.processed = processed;
  }



}







