package com.proof.of.concept.exceptions;

public class NumberOfTicketException  extends Exception {
    public NumberOfTicketException(Integer numberOfTickets){
        super("There isn't enough free tickets. Tickets left:"+numberOfTickets);
    }
}