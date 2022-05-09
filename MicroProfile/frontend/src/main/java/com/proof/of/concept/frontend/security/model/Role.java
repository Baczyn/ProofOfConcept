package com.proof.of.concept.frontend.security.model;

import java.util.function.Supplier;

public enum Role implements Supplier<String> {

    ADMIN, USER;

    @Override
    public String get() {
        return this.name();
    }
}
