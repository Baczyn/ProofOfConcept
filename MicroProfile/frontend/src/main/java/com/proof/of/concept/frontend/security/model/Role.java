package com.proof.of.concept.frontend.security.model;

import jakarta.json.bind.annotation.JsonbTransient;
import org.bson.Document;

import java.util.function.Supplier;

public enum Role implements Supplier<String> {

    ADMIN, MANAGER, USER;

    @Override
    public String get() {
        return this.name();
    }
}
