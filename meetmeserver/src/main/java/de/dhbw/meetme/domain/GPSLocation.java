package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class GPSLocation extends PersistentObject {

  private String username;
  private double longitude, latitude;
  private double timeStamp;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latidude) {
    this.latitude = latidude;
  }

  public double getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(double timeStamp) {
    this.timeStamp = timeStamp;
  }

  public GPSLocation(String username, double latidude, double longitude, double timeStamp){
    this.username=username;
    this.longitude=longitude;
    this.latitude=latidude;
    this.timeStamp=timeStamp;
  }

}