package com.proof.of.concept.frontend;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.Claims;

import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
@Named
public class LoginBean {


    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // tag::doLogin[]
    public String doLogin() throws Exception {
        HttpServletRequest request = SessionUtils.getRequest();
//
////        try {
////            request.logout();
////            request.login(this.username, this.password);
////        } catch (ServletException e) {
////            System.out.println("Login failed.");
////            return "error.jsf";
////        }
//        request.logout();
//        if(username.equals("bob")){
//            System.out.println("Authenticated");
//            request.setAttribute("role","admin");
//            request.setAttribute("user","bob");
//        }
//        else {
//            return "error.jsf";
//        }

//        String remoteUser = request.getRemoteUser();
//        System.out.println("DUPA"+remoteUser);
//        Set<String> roles = getRoles(request);
          Set<String> roles = new HashSet<>();
          roles.add("admin");
//        String jwt = buildJwt(username, new HashSet<String>(Collections.singleton("admin")));
        String jwt = JwtBuilder.create("jwtFrontEndBuilder")
                // end::jwtBuilder[]
                .claim(Claims.SUBJECT, "bob")
                .claim("upn", "bob")
                // tag::claim[]
                .claim("groups", roles.toArray(new String[roles.size()]))
                .claim("aud", "dupaServices")
                // end::claim[]
                .buildJwt()
                .compact();
        System.out.println(jwt);
            HttpSession ses = request.getSession();
            if (ses == null) {
                System.out.println("Session timed out. ");
            } else {
                // tag::setAttribute[]
                ses.setAttribute("jwt", jwt);
                // end::setAttribute[]
            }
//        if (remoteUser != null && remoteUser.equals(username)) {
//            String jwt = buildJwt(username, roles);
//            HttpSession ses = request.getSession();
//            if (ses == null) {
//                System.out.println("Session timed out.");
//            } else {
//                // tag::setAttribute[]
//                ses.setAttribute("jwt", jwt);
//                // end::setAttribute[]
//            }
//        } else {
//            System.out.println("Failed to update JWT in session.");
//        }
        return "application.jsf";
    }
    // end::doLogin[]
    // tag::buildJwt[]

  private String buildJwt(String userName, Set<String> roles) throws Exception {
        // tag::jwtBuilder[]
        return JwtBuilder.create("jwtFrontEndBuilder")
        // end::jwtBuilder[]
                         .claim(Claims.SUBJECT, userName)
                         .claim("upn", userName)
                         // tag::claim[]
                         .claim("groups", roles.toArray(new String[roles.size()]))
                         .claim("aud", "dupaServices")
                         // end::claim[]
                         .buildJwt()
                         .compact();

    }
    // end::buildJwt[]

    private Set<String> getRoles(HttpServletRequest request) {
        Set<String> roles = new HashSet<>();
        boolean isAdmin = request.isUserInRole("admin");
        boolean isUser = request.isUserInRole("user");
        if (isAdmin) {
            roles.add("admin");
        }
        if (isUser) {
            roles.add("user");
        }
        return roles;
    }
}
