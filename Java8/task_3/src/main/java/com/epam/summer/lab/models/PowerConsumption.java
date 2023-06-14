package com.epam.summer.lab.models;

import lombok.*;

import java.time.LocalDate;

import static com.epam.summer.lab.utils.RandomUtils.randomizer;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PowerConsumption {
    private final LocalDate period;
    private  long powerConsumption;

    public PowerConsumption(LocalDate period) {
        this.period = period;
        this.powerConsumption = randomizer();
    }
}