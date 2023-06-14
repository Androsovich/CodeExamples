package com.epam.summer.lab.utils.generator;

import com.epam.summer.lab.models.PowerConsumption;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class GeneratePowerConsumption {
    /**
     * @param startDay -  the date is represented as long by EpochDay(this day is the beginning of the count of days)
     * @param endDay   - the date is represented as long by EpochDay(on this day, the counting of days ends inclusive)
     */
    public static List<PowerConsumption> getPowerConsumptionForPeriod(long startDay, long endDay) {
        return
                LongStream
                        .rangeClosed(startDay, endDay)

                        //convert day represented as long to LocalDate
                        .mapToObj(LocalDate::ofEpochDay)

                        //init new PowerConsumptionFactory class
                        .map(PowerConsumption::new)

                        //collect to List
                        .collect(Collectors.toList());
    }
}