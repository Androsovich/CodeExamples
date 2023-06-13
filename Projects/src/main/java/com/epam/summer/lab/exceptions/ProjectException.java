package com.epam.summer.lab.exceptions;

public class ProjectException extends RuntimeException {
    public ProjectException() {
    }

    public ProjectException(String message) {
        super(message);
    }
}