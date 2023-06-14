package com.epam.summer.lab.constants;

import com.epam.summer.lab.models.PowerConsumption;

import java.time.LocalDate;

public class Constants {
    public static final long START_DAY = LocalDate.of(2020, 1, 1).toEpochDay();
    public static final long END_DAY = LocalDate.of(2020, 12, 31).toEpochDay();

    public static final PowerConsumption POWER_DEFAULT = new PowerConsumption(null, -1L);
}