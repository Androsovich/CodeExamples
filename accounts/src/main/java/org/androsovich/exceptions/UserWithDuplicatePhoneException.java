package org.androsovich.exceptions;

public class UserWithDuplicatePhoneException extends RuntimeException {
    public UserWithDuplicatePhoneException(String message) {
        super(message);
    }
}
