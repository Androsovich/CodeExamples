package org.androsovich.exceptions;

public class AccountWithDuplicateUserException extends RuntimeException {
    public AccountWithDuplicateUserException(String message) {
        super(message);
    }
}
