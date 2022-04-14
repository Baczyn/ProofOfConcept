package com.proof.of.concept.rest;

import com.proof.of.concept.model.Service;
import com.proof.of.concept.repository.ServiceRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("service")
public class ServiceManagementResourceApi {

    @Inject
    private ServiceRepository serviceRepository;

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServiceByAddressId(@QueryParam("addressId") Integer addressId){
        System.out.println(addressId);
        Service service = serviceRepository.getServiceByAddressId(addressId);
        return Response
                .status(service == null?Response.Status.NOT_FOUND:Response.Status.OK)
                .entity(service)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response putService(Service service){
        System.out.println(service);
        Service serviceUpdated = serviceRepository.updateActive(service);
        return Response
                .status(Response.Status.CREATED)
                .entity(serviceUpdated)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}