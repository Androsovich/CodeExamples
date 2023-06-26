package org.androsovich.drivers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Money {
    private final String currency;
    private final String amount;
}
