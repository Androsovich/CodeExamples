package org.androsovich.exceptions;

public class UserNotFoundByEmailException extends RuntimeException{
    public UserNotFoundByEmailException(String message) {
        super(message);
    }
}
