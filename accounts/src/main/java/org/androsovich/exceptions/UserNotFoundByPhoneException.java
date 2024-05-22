package org.androsovich.exceptions;

public class UserNotFoundByPhoneException extends RuntimeException {
    public UserNotFoundByPhoneException(String message) {
        super(message);
    }
}
