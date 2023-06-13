package com.epam.summer.lab.utils.validators;

public interface StatusValidator {

    boolean checkStatus(String status);

    String[] getStatuses();
}