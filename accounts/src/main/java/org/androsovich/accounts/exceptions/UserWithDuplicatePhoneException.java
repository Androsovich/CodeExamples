package org.androsovich.accounts.exceptions;

public class UserWithDuplicatePhoneException extends UserException {
    public UserWithDuplicatePhoneException(String message) {
        super(message);
    }
}
