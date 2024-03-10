package org.androsovich.applications.exceptions;

public class BidNotFoundException extends RuntimeException{
    public BidNotFoundException(String message) {
        super(message);
    }
}
