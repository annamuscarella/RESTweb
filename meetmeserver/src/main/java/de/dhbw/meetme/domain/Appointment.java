package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class Appointment extends PersistentObject {

  private String lecturerName;
  private String date;
  private String proposedTime;
  private String studentName;
  private String studentMail;

  public String getLecturerName() {
    return lecturerName;
  }

  public void setLecturerName(String lecturerName) {
    this.lecturerName = lecturerName;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getProposedTime() {
    return proposedTime;
  }

  public void setProposedTime(String proposedTime) {
    this.proposedTime = proposedTime;
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


  public Appointment(String lecturerName, String date, String proposedTime, String studentName, String studentMail) {
    this.lecturerName = lecturerName;
    this.date = date;
    this.proposedTime = proposedTime;
    this.studentName = studentName;
    this.studentMail = studentMail;
  }



}







