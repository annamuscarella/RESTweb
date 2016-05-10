package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class AppReply extends PersistentObject {

  private String lecturerName;
  private String message;
  private String personalMessage;
  private String reply;
  private boolean processed;

  public String getLecturerName() {
    return lecturerName;
  }

  public void setLecturerName(String lecturerName) {
    this.lecturerName = lecturerName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPersonalMessage() {
    return personalMessage;
  }

  public void setPersonalMessage(String personalMessage) {
    this.personalMessage = personalMessage;
  }

  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public boolean isProcessed() {
    return processed;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }

  public AppReply(String lecturerName, String message, String personalMessage, String reply, boolean processed) {
    this.lecturerName = lecturerName;
    this.message = message;
    this.personalMessage = personalMessage;
    this.reply = reply;
    this.processed = processed;
  }
}







