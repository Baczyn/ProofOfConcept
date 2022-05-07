package com.proof.of.concept.frontend.security.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("User does not found with username: " + id);
    }
}
