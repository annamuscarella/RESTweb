package de.dhbw.meetme.domain;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@Entity // also add to persistence.xml !!
@XmlRootElement // needed for REST JSON marshalling
public class User extends PersistentObject {
  private String name;
  private String email;
  private String password; //able to get and set passwort once the user is created??

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
