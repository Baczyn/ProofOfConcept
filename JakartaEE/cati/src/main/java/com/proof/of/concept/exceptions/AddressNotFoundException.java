package com.proof.of.concept.exceptions;

public class AddressNotFoundException extends Exception{
    public AddressNotFoundException(){
        super("Address do not exists.");
    }
}