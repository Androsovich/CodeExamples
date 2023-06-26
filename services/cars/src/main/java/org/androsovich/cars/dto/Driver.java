package org.androsovich.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Driver {
    private final Long id;
    private  final String fullName;
}