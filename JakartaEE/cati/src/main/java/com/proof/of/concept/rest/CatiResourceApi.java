package com.proof.of.concept.rest;

import com.proof.of.concept.exceptions.AddressNotFoundException;
import com.proof.of.concept.model.Address;
import com.proof.of.concept.model.CatiResponse;
import com.proof.of.concept.repository.CatiRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("address")
public class CatiResourceApi {

    @Inject
    private CatiRepository catiRepository;

    @POST()
    @Path("get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressId(Address address) {
        System.out.println(address);
        CatiResponse catiResponse;
        try {
            int id = catiRepository.getAddressId(address);
            catiResponse = new CatiResponse(id);
            catiResponse.setErrorCode(Response.Status.OK.toString());
        } catch (AddressNotFoundException e) {
            System.err.println(e.getMessage());
            catiResponse = new CatiResponse(e.getClass().getSimpleName(),e.getMessage());
        }
        return Response
                .status(Response.Status.OK)
                .entity(catiResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(Address address){
        catiRepository.createAddress(address);
        return Response
                .status(Response.Status.OK)
                .entity(address)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAddress(@PathParam("id") int addressId){
        Address address = catiRepository.deleteAddress(addressId);
        return Response
                .status(address==null?Response.Status.NOT_FOUND:Response.Status.OK)
                .entity(address)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyAddress(Address address){
        Address addressModified = catiRepository.modifyAddress(address);
        return Response
                .status(address==null?Response.Status.NOT_FOUND:Response.Status.OK)
                .entity(addressModified)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAddress(){
        List<Address> addressList = catiRepository.getAllAddress();
        return Response
                .status(Response.Status.OK)
                .entity(addressList)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}