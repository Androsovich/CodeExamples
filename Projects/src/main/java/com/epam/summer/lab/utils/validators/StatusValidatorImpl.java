package com.epam.summer.lab.utils.validators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Getter
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class StatusValidatorImpl implements StatusValidator {

    @Value("${api.statuses}")
    private final String[] statuses;

    public boolean checkStatus(String status) {
        return Arrays.asList(statuses).contains(status);
    }
}