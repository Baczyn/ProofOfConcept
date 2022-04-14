package com.proof.of.concept.validator;

import com.proof.of.concept.model.SystemRequest;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class AddressValidator implements Validator {

    private boolean validateStringValues(String value) {
        return value.matches(".*[0-9].*");
    }

    private boolean validatePostalCode(String value) {
        return value.matches("^[0-9]{2}(?:-[0-9]{3})?$");
    }

    private boolean validateStreetNumber(String value) {
        return value.matches("^(0|[1-9]\\d*)$");
    }


    @Override
    public boolean validate(SystemRequest address) {
        if (validateStringValues(address.getCountry())) {
            System.out.println("Country:" + address.getCountry() + " is incorrect.");
            return false;
        }
        if (validateStringValues(address.getCity())) {
            System.out.println("City:" + address.getCity() + " is incorrect.");
            return false;
        }
        if (validateStringValues(address.getStreet())) {
            System.out.println("Street:" + address.getStreet() + " is incorrect.");
            return false;
        }
        if (!validatePostalCode(address.getPostalCode())) {
            System.out.println("PostalCode:" + address.getPostalCode() + " is incorrect.");
            return false;
        }
        if (!validateStreetNumber(address.getStreetNumber())) {
            System.out.println("StreetNumber:" + address.getStreetNumber() + " is incorrect.");
            return false;
        }
        return true;
    }
}