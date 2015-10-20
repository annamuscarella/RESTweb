package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity// also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class UserPosition{

  private String name;
  private double lat, lon;
  private String color;

  public UserPosition(String name, double lat, double lon, String color){
    this.name=name;
    this.lat=lat;
    this.lon=lon;
    this.color=color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String toString(){
      String s = "UserPosition from " + name + " is: " + lat + ", " + lon + "; " + color;
      return s;
  }



}
