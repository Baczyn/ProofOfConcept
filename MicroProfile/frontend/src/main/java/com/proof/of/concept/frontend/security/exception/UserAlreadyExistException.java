package com.proof.of.concept.frontend.security.exception;

import java.util.Collections;
import java.util.List;

public class UserAlreadyExistException extends RuntimeException {

    private final String message;

    public UserAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

    public List<String> getMessages() {
        return Collections.singletonList(message);
    }
}
