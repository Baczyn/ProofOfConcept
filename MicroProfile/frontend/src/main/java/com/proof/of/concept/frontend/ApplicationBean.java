package com.proof.of.concept.frontend;

import com.proof.of.concept.frontend.client.BookingClient;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@ApplicationScoped
@Named
public class ApplicationBean {

    @Inject
    @RestClient
    private BookingClient bookingClient;

    public String getJwt() {
        String jwtTokenString = SessionUtils.getJwtToken();
        return "Bearer " + jwtTokenString;
    }

//    public String getOs() {
//        String authHeader = getJwt();
//        String os;
//        try {
//            os = bookingClient.getOS(authHeader);
//        } catch (Exception e) {
//            return "You are not authorized to access this system property";
//        }
//        return os;
//    }

    public String getUsername() {
        String authHeader = getJwt();
        return bookingClient.getUsername(authHeader);
    }

    public String getJwtRoles() {
        String authHeader = getJwt();
        return bookingClient.getJwtRoles(authHeader);

    }
}
