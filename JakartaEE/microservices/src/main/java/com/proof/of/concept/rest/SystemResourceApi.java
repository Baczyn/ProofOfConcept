package com.proof.of.concept.rest;

import com.proof.of.concept.model.SystemRequest;
import com.proof.of.concept.model.SystemResponse;
import com.proof.of.concept.model.cati.CatiRequest;
import com.proof.of.concept.model.cati.CatiResponse;
import com.proof.of.concept.model.serviceManagement.PutServiceManagementRequest;
import com.proof.of.concept.model.serviceManagement.ServiceManagementResponse;
import com.proof.of.concept.rest.client.CatiClient;
import com.proof.of.concept.rest.client.ServiceManagementClient;
import com.proof.of.concept.validator.Validator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Path("system")
public class SystemResourceApi {

    @Inject
    CatiClient catiClient;

    @Inject
    ServiceManagementClient serviceManagementClient;

    @Inject
    Validator validator;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateService(List<SystemRequest> systemRequests) {

        /** filter invalid systemRequests and add them to systemResponseList */
        List<SystemResponse> systemResponseList = systemRequests.stream().
                filter(Predicate.not(validator::validate)).
                map(request -> new SystemResponse(request, Response.Status.NOT_ACCEPTABLE.toString(), "Address is not valid.")).
                collect(Collectors.toList());

        /** Variable holds requests which will be sent to ServiceManagement */
        List<Integer> systemManageRequestList = new ArrayList<>();

        /** send only valid request to external microservice (CATI) and get corresponding addressId, add them to systemResponseList */
        systemRequests.stream().
                filter(validator::validate).
                forEach(request -> {
                            CatiResponse response = catiClient.convertAddressToId(new CatiRequest(request));
                            systemResponseList.add(new SystemResponse(request, response.getId(), response.getErrorCode(), response.getErrorDesc()));
                            if (response.getErrorCode().equals(Response.Status.OK.toString()))
                                systemManageRequestList.add(response.getId());
                        }
                );

        /** send request to external microservice (ServiceManagement) - checking if service activation is possible */
        List<ServiceManagementResponse> serviceManagementResponse = systemManageRequestList.stream().
                map(serviceManagementClient::checkServiceAvailability).
                collect(Collectors.toList());

        /** update systemResponse */
        serviceManagementResponse.
                forEach(response -> systemResponseList.
                        stream().
                        filter(request -> response.getAddressId() == request.getAddressId()).
                        findFirst().
                        ifPresent(found ->
                        {
                            if (response.isActive())
                                found.setErrorDesc("Service is active in that location.");
                            else
                                found.setErrorDesc(response.getErrorDesc());
                            found.setErrorCode(response.getErrorCode());
                        })
                );

        /** filter services to activate and send request to microservice(ServiceManagement)*/
        List<ServiceManagementResponse> putServiceManagementResponse = serviceManagementResponse.stream().
                filter(r -> r.getErrorCode().equals(Response.Status.OK.toString())).
                filter(r -> !r.isActive()).map(PutServiceManagementRequest::new).
                peek(r -> r.setActive(true)).
                map(serviceManagementClient::modifyService).
                peek(r -> {
                    if (r.getErrorCode().equals(Response.Status.CREATED.toString())) {
                        r.setErrorDesc("Service has been activated.");
                    }
                }).
                collect(Collectors.toList());
        /** update systemResponse */
        putServiceManagementResponse.forEach(response -> systemResponseList.
                stream().
                filter(request -> response.getAddressId() == request.getAddressId()).
                findFirst().
                ifPresent(found ->
                {
                    found.setErrorCode(response.getErrorCode());
                    found.setErrorDesc(response.getErrorDesc());
                })
        );
        /** build Response */
        return Response
                .status(Response.Status.OK).
                entity(systemResponseList).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateService(List<SystemRequest> systemRequests) {

        //filter invalid systemRequests and add them to systemResponseList
        List<SystemResponse> systemResponseList = systemRequests.stream().
                filter(Predicate.not(validator::validate)).
                map(request -> new SystemResponse(request, Response.Status.NOT_ACCEPTABLE.toString(), "Address is not valid.")).
                collect(Collectors.toList());

        /** Variable holds requests which will be send to ServiceManagement */
        List<Integer> systemManageRequestList = new ArrayList<>();

        // send only valid request to external microservice (CATI) and get corresponding addressId, add them to systemResponseList
        systemRequests.stream().
                filter(validator::validate).
                forEach(request -> {
                            CatiResponse response = catiClient.convertAddressToId(new CatiRequest(request));
                            systemResponseList.add(new SystemResponse(request, response.getId(), response.getErrorCode(), response.getErrorDesc()));
                            if (response.getErrorCode().equals(Response.Status.OK.toString()))
                                systemManageRequestList.add(response.getId());
                        }
                );

        // send request to external microservice (ServiceManagement) - checking if service activation is possible
        List<ServiceManagementResponse> serviceManagementResponse = systemManageRequestList.stream().
                map(serviceManagementClient::checkServiceAvailability).
                collect(Collectors.toList());

        //update systemResponse
        serviceManagementResponse.forEach(response -> systemResponseList.stream().
                filter(request -> response.getAddressId() == request.getAddressId()).findFirst().ifPresent(found -> {
                    if (!response.isActive()) {
                        found.setErrorDesc("Service is inactive in that location.");
                    }
                    found.setErrorCode(response.getErrorCode());
                })
        );

        // filter services to activate and send request to microservice(ServiceManagement)
        List<ServiceManagementResponse> putServiceManagementResponse = serviceManagementResponse.stream().
                filter(r -> r.getErrorCode().equals(Response.Status.OK.toString())).
                filter(ServiceManagementResponse::isActive).map(PutServiceManagementRequest::new).
                peek(r -> r.setActive(false)).
                map(serviceManagementClient::modifyService).
                peek(r -> {
                    if (r.getErrorCode().equals(Response.Status.CREATED.toString())) {
                        r.setErrorDesc("Service has been deactivated.");
                    }
                }).
                collect(Collectors.toList());

        putServiceManagementResponse.forEach(response -> systemResponseList.stream().
                filter(request -> response.getAddressId() == request.getAddressId()).findFirst().
                ifPresent(found -> {
                    found.setErrorCode(response.getErrorCode());
                    found.setErrorDesc(response.getErrorDesc());
                })
        );

        return Response
                .status(Response.Status.OK)
                .entity(systemResponseList)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}