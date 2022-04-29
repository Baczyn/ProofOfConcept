package com.proof.of.concept.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.Claim;

@RequestScoped
@Path("/booking")
public class SystemResource {

    @Inject
    @Claim("groups")
    private JsonArray roles;

    @GET
    @Path("/username")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public String getUsername() {
        return System.getProperties().getProperty("user.name");
    }

    @GET
    @Path("/os")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    public String getOS() {
        return System.getProperties().getProperty("os.name");
    }

    @GET
    @Path("/jwtroles")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public String getRoles() {
        return roles.toString();
    }

}
