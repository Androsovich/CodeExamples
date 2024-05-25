package org.androsovich.accounts.exceptions;

public class UserWithDuplicateEmailException extends UserException {
    public UserWithDuplicateEmailException(String message) {
        super(message);
    }
}
