package org.androsovich.exceptions;

public class UserWithDuplicateEmailException extends RuntimeException {
    public UserWithDuplicateEmailException(String message) {
        super(message);
    }
}
