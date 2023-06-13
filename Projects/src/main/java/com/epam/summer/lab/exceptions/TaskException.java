package com.epam.summer.lab.exceptions;

public class TaskException extends RuntimeException {
    public TaskException() {
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(String message) {
        super(message);
    }
}