package org.androsovich.exceptions;

public class IllegalUpdateUserException extends RuntimeException {
    public IllegalUpdateUserException(String message) {
        super(message);
    }
}
