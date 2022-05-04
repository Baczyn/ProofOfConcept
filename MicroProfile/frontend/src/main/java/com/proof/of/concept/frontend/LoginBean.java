package com.proof.of.concept.frontend;

import com.ibm.websphere.security.jwt.Claims;
import com.ibm.websphere.security.jwt.JwtBuilder;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
@Named
@Data
public class LoginBean {

    private String username;
    private String password;

    public String doLogin() throws Exception {

        HttpServletRequest request = SessionUtils.getRequest();
        //map role and user
        //TODO - check user in DB
        Set<String> roles = new HashSet<>();
        roles.add("admin");

        try {
            request.logout();
            String jwt = buildJwt(username, roles);
            System.out.println(jwt);

            HttpSession ses = request.getSession();
            if (ses == null) {
                System.out.println("Session timed out. ");
            } else {
                ses.setAttribute("jwt", jwt);
                ses.setAttribute("role", "admin");
            }

        } catch (Exception e) {
            System.out.println("Login failed.");
            return "error.jsf";
        }
        return "index.jsf?faces-redirect=true";
    }

    private String buildJwt(String userName, Set<String> roles) throws Exception {
        return JwtBuilder.create("jwtFrontEndBuilder")
                .claim(Claims.SUBJECT, userName)
                .claim("upn", userName)
                .claim("groups", roles.toArray(new String[roles.size()]))
                .claim("aud", "frontendService")
                .buildJwt()
                .compact();
    }
}
