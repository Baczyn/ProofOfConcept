package com.proof.of.concept.exceptions;

public class EventNotFoundException extends Exception{
    public EventNotFoundException(){
        super("Event do not exists.");
    }
}