// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2018, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
// tag::jwt[]
package com.proof.of.concept.frontend.util;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
    return (String) getSession().getAttribute("jwt");
  }

}
// end::jwt[]