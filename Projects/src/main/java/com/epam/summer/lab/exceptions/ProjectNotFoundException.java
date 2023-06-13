package com.epam.summer.lab.exceptions;

public class ProjectNotFoundException extends ProjectException {
    public ProjectNotFoundException() {
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}