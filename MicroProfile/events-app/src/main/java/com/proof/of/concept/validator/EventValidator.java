package com.proof.of.concept.validator;

import com.proof.of.concept.model.Event;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

@ApplicationScoped
public class EventValidator {

    @Inject
    private Validator validator;

    public JsonArray getViolations(Event event) {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        JsonArrayBuilder messages = Json.createArrayBuilder();

        violations.forEach(v -> messages.add(v.getMessage()));

        return messages.build();
    }
}
