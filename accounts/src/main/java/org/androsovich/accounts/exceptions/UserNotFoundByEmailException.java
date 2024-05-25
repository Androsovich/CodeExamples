package org.androsovich.accounts.exceptions;

public class UserNotFoundByEmailException extends UserException {
    public UserNotFoundByEmailException(String message) {
        super(message);
    }
}
