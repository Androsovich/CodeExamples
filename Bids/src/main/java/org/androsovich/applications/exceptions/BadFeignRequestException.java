package org.androsovich.applications.exceptions;

public class BadFeignRequestException extends RuntimeException {
    public BadFeignRequestException(String message) {
        super(message);
    }
}
