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
  private String studentFName;
  private String studenLName;
  private String studentMail;
  private String course;
  private String topic;

  public String getStudentFName() {
    return studentFName;
  }

  public void setStudentFName(String studentFName) {
    this.studentFName = studentFName;
  }

  public String getStudenLName() {
    return studenLName;
  }

  public void setStudenLName(String studenLName) {
    this.studenLName = studenLName;
  }

  public String getCourse() {
    return course;
  }

  public void setCourse(String course) {
    this.course = course;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }



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
    return studentFName;
  }

  public void setStudentName(String studentName) {
    this.studentFName = studentFName;
  }

  public String getStudentMail() {
    return studentMail;
  }

  public void setStudentMail(String studentMail) {
    this.studentMail = studentMail;
  }


  public Appointment(String lecturerName, String date, String proposedTime, String studentFName,String studenLName, String studentMail, String course, String topic) {
    this.lecturerName = lecturerName;
    this.date = date;
    this.proposedTime = proposedTime;
    this.studentFName = studentFName;
    this.studenLName=studenLName;
    this.studentMail = studentMail;
    this.course=course;
    this.topic=topic;
  }



}







