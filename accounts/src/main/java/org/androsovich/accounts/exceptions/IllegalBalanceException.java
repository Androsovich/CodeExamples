package org.androsovich.accounts.exceptions;

public class IllegalBalanceException extends RuntimeException {
    public IllegalBalanceException(String message) {
        super(message);
    }
}
