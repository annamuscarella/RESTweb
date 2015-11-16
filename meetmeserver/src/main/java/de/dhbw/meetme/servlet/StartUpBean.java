package de.dhbw.meetme.servlet;

import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.SQLException;
import java.util.Collection;

/**
 *
 */
@Singleton
@Startup
public class StartUpBean {
  private static final Logger log = LoggerFactory.getLogger(StartUpBean.class);
  public static final String DB_PORT = "9092";

  @Inject
  UserDao userDao;

  @PostConstruct
  public void init() {
    log.info("MeetMe Server started.");
    // inital code goes here

    startDbServer();
   createUserDoc();
  }

  @PreDestroy
  public void shutdown() {
    stopDbServer();
  }

  /**
   * Start H2 db as server.
   * You can connect remotly using this URL:
   * jdbc:h2:tcp://localhost:9092/~/meetmedb
   * User: sa
   * Pwd: <KEEP EMPTY>
   *
   * WARNING: Server is NOT secured. Don't use in production!!!!!
   */
  private void startDbServer() {
    try {
      Server.createTcpServer("-tcpPort", DB_PORT, "-tcpAllowOthers").start();
      log.warn("WARNING: H2 Server started. Only for testing allowed! Don't start on production system!!!!!");
    } catch (SQLException e) {
      log.error("Could not start db server: " + e);
    }
  }

  private static void stopDbServer() {
    try {
      Server.shutdownTcpServer("tcp://localhost:" + DB_PORT, "", true, true);
    } catch (SQLException e) {
      log.error("Could not shutdown db server: " + e);
    }
  }

  public void createUserDoc(){
    log.debug("1");
    try
    {
      log.debug("2");
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

      //root elements
      Document doc = docBuilder.newDocument();

      Element rootElement = doc.createElement("tomcat-users");
      doc.appendChild(rootElement);
      log.debug("3");
      //staff elements
      Element role = doc.createElement("role");
      rootElement.appendChild(role);
      log.debug("3.1");
      //set attribute to staff element
      Attr attr = doc.createAttribute("role");
      attr.setValue("tomcat");
      role.setAttributeNode(attr);
      rootElement.appendChild(role);
      log.debug("3.2");
      Collection<User> users = userDao.list();
      log.debug("4");
      for(User myuser:users) {
        Element user = doc.createElement("user");
        Attr attruser = doc.createAttribute("username");
        attruser.setValue(myuser.getName());
        user.setAttributeNode(attruser);
        Attr attrpw = doc.createAttribute("password");
        attrpw.setValue(myuser.getPassword());
        user.setAttributeNode(attrpw);
        Attr attrrole = doc.createAttribute("roles");
        attrrole.setValue("tomcat");
        user.setAttributeNode(attrrole);
        rootElement.appendChild(user);
      }
      log.debug("5");
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      log.debug("6");
      StreamResult result =  new StreamResult(new File("/Users/macapple/Progammierung/meetme/meetme/meetmeserver/conf/tomcat-users.xml"));
      transformer.transform(source, result); log.debug("durchgelaufen");


    } catch (ParserConfigurationException e) {
      e.printStackTrace();

    }catch(TransformerException tfe){
      tfe.printStackTrace();
    }
  }
}

