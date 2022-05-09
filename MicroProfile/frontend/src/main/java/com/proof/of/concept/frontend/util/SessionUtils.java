package com.proof.of.concept.frontend.util;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class SessionUtils {
    // Gets the current session for a logged in user.
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                .getSession(false);
    }

    // Gets Http servlet for user request information.
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getJwtToken() {
        return "Bearer " + getSession().getAttribute("jwt");
    }

    public static String getCurrentUserName() {
        return Optional.ofNullable(getSession().getAttribute("user")).orElse("").toString();
    }

    public static String getEventIdFormParam() {
        return Optional.ofNullable(getRequest().getParameter("id")).orElse("");
    }
}
