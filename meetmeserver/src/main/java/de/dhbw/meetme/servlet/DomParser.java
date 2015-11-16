package de.dhbw.meetme.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GPSLocation;
import de.dhbw.meetme.domain.User;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by macapple on 16.11.15.
 */
public class DomParser {
    @Inject
    UserDao userDao;

    public void createUserDoc(){
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("tomcat-users");
            doc.appendChild(rootElement);

            //staff elements
            Element role = doc.createElement("role");
            rootElement.appendChild(role);

            //set attribute to staff element
            Attr attr = doc.createAttribute("role");
            attr.setValue("tomcat");
            role.setAttributeNode(attr);
            rootElement.appendChild(role);
            Collection<User> users = userDao.list();

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
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File("/Users/macapple/Progammierung/meetme/meetme/meetmeserver/conf/tomcat-users1.xml"));
            transformer.transform(source, result);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        }catch(TransformerException tfe){
        tfe.printStackTrace();
    }
    }

}
