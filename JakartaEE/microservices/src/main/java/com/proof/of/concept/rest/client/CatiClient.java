package com.proof.of.concept.rest.client;

import com.proof.of.concept.model.cati.CatiRequest;
import com.proof.of.concept.model.cati.CatiResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class CatiClient {

    private static final String url = "http://localhost:9081/cati/api/address/get";

    public CatiResponse convertAddressToId(CatiRequest catiRequest){
        CatiResponse catiResponse = new CatiResponse();
        Client client = ClientBuilder.newClient();
        try {
            Response response = client.target(url).request().post(Entity.entity(catiRequest, MediaType.APPLICATION_JSON_TYPE));
            catiResponse = response.readEntity(CatiResponse.class);
            response.close();
        }
        catch (Exception e) {
            catiResponse.setErrorCode(e.getClass().getSimpleName());
            catiResponse.setErrorDesc("CATI microservice is unavailable.");
        }
        client.close();
        return catiResponse;
    }
}