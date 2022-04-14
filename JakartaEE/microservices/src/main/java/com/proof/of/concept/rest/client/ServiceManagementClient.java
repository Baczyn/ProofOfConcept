package com.proof.of.concept.rest.client;

import com.proof.of.concept.model.serviceManagement.PutServiceManagementRequest;
import com.proof.of.concept.model.serviceManagement.ServiceManagementResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class ServiceManagementClient {
    private static final String url = "http://localhost:9082/serviceManagement/api/service";

    public ServiceManagementResponse checkServiceAvailability(Integer addressId){
        ServiceManagementResponse serviceManagementResponse = new ServiceManagementResponse();
        Client client = ClientBuilder.newClient();
        try {
            Response response = client.target(url + "?addressId=" + addressId).request().get();
            if (response.getStatusInfo() != Response.Status.NOT_FOUND)
                serviceManagementResponse = response.readEntity(ServiceManagementResponse.class);
            else {
                serviceManagementResponse.setAddressId(addressId);
                serviceManagementResponse.setErrorDesc("Service is unavailable in that location.");
            }
            serviceManagementResponse.setErrorCode(response.getStatusInfo().toString());

            response.close();
        }
        catch (Exception e) {
            serviceManagementResponse.setAddressId(addressId);
            serviceManagementResponse.setErrorCode(e.getClass().getSimpleName());
            serviceManagementResponse.setErrorDesc("ServiceManagement microservice is unavailable.");
        }
        client.close();
        return serviceManagementResponse;
    }

    public ServiceManagementResponse modifyService(PutServiceManagementRequest request){
        ServiceManagementResponse serviceManagementResponse = new ServiceManagementResponse();
        Client client = ClientBuilder.newClient();
        try {
            Response response = client.target(url).request().put(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
            serviceManagementResponse = response.readEntity(ServiceManagementResponse.class);
            serviceManagementResponse.setErrorCode(response.getStatusInfo().toString());
            response.close();
        }
        catch (Exception e) {
            serviceManagementResponse.setErrorCode(e.getClass().getSimpleName());
            serviceManagementResponse.setErrorDesc("ServiceManagement microservice is unavailable.");
        }

        client.close();
        return serviceManagementResponse;
    }
}