package org.androsovich.applications.exceptions;

public class BadFeignResponseException extends RuntimeException{
    public BadFeignResponseException(String message) {
        super(message);
    }
}
