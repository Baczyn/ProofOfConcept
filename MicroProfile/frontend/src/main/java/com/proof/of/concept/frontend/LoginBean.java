package com.proof.of.concept.frontend;

import com.ibm.websphere.security.jwt.Claims;
import com.ibm.websphere.security.jwt.JwtBuilder;
import com.proof.of.concept.frontend.security.model.Role;
import com.proof.of.concept.frontend.security.model.User;
import com.proof.of.concept.frontend.security.service.SecurityService;
import com.proof.of.concept.frontend.util.SessionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@ApplicationScoped
@Named
@Data
public class LoginBean {

    @Inject
    SecurityService securityService;

    private String username;
    private String password;


    public String doRegister() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        try {
            User user = securityService.create(new User(username, password, roles));
            System.out.println("Created user:" + user);

            HttpServletRequest request = SessionUtils.getRequest();
            request.logout();

            String jwt = buildJwt(username, roles);
            System.out.println(jwt);

            HttpSession ses = request.getSession();
            if (ses == null) {
                System.out.println("Session timed out.");
            } else {
                ses.setAttribute("jwt", jwt);
                ses.setAttribute("user", username);
                ses.setAttribute("role", roles.iterator().next().name().toLowerCase(Locale.ROOT));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error.jsf";
        }
        return "index.jsf?faces-redirect=true";
    }

    public String doLogin() throws Exception {

        HttpServletRequest request = SessionUtils.getRequest();
        try {
            request.logout();
            User user = securityService.login(new User(username, password, null));
            System.out.println(user);

            String jwt = buildJwt(user.getName(), user.getRoles());
            System.out.println(jwt);

            HttpSession ses = request.getSession();
            if (ses == null) {
                System.out.println("Session timed out. ");
            } else {
                ses.setAttribute("jwt", jwt);
                ses.setAttribute("user", user.getName());
                ses.setAttribute("role", user.getRoles().iterator().next().name().toLowerCase(Locale.ROOT));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "error.jsf";
        }
        return "index.jsf?faces-redirect=true";
    }

    private String buildJwt(String userName, Set<Role> roles) throws Exception {
        return JwtBuilder.create("jwtFrontEndBuilder")
                .claim(Claims.SUBJECT, userName)
                .claim("upn", userName)
                .claim("groups", roles)
                .claim("aud", "frontendService")
                .buildJwt()
                .compact();
    }
}
