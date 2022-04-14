package com.proof.of.concept.validator;

import com.proof.of.concept.model.SystemRequest;

@FunctionalInterface
public interface Validator {
    abstract boolean validate(SystemRequest address);
}
