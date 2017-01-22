package com.sopra.agile.cardio.app.cucumber.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sopra.agile.cardio.app.cucumber.step.Steps;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(Steps.class);

    private static final Client client = Client.create();
    private static final String getUrl = "http://localhost:5678/api/version";

    private RestClient() {
        // static class
    }

    public static boolean isAPIStarted() {
        boolean status = false;
        try {
            WebResource webResource = client.resource(getUrl);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() == 200) {
                status = true;
            }
        } catch (Exception ex) {
            status = false;
        }
        return status;
    }

}
