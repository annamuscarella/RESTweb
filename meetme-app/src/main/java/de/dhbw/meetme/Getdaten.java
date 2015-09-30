package de.dhbw.meetme;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Created by mahandru on 29.09.2015.
 */
public class Getdaten {

    private void callClient() {
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target("http://localhost:8087/meetmeserver/api");
        String responseMsg = target.path("user/list").request().get(String.class);
        System.out.println(responseMsg);
    }


}
