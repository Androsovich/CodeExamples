package com.epam.summer.lab.calculators;

import com.epam.summer.lab.collectors.PowerCollector;
import com.epam.summer.lab.constants.Constants;
import com.epam.summer.lab.models.PowerConsumption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Month;
import java.util.*;

import static com.epam.summer.lab.constants.Constants.*;
import static com.epam.summer.lab.utils.generator.GeneratePowerConsumption.getPowerConsumptionForPeriod;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingLong;

public class PowerCalculator {
    private static final Logger logger = LoggerFactory.getLogger(PowerCalculator.class);

    private static final List<PowerConsumption> powerConsumptions = initPowerConsumption();

    private static List<PowerConsumption> initPowerConsumption() {
        return getPowerConsumptionForPeriod(START_DAY, END_DAY);
    }

    public static void run(){
        calculateMaxPowerByYear();
        calculateMinPowerByYear();
        calculateAveragePowerByYear();
        calculateSummaryStatisticsForMonth();
        sequentialCollectMaxPowerForMonths();
        parallelCollectMaxPowerForMonths();
    }

    //1) maximum value for the year + date;
    private static void calculateMaxPowerByYear() {
        PowerConsumption maxPowerConsumption =
                powerConsumptions
                        .stream()
                        .max(Comparator.comparingLong(PowerConsumption::getPowerConsumption))
                        .orElse(Constants.POWER_DEFAULT);
        logger.info("max power consumption for year : {} ", maxPowerConsumption);
    }

    //1) minimum value for the year + date;
    private static void calculateMinPowerByYear() {
        PowerConsumption minPowerConsumption =
                powerConsumptions
                        .stream()
                        .min(Comparator.comparingLong(PowerConsumption::getPowerConsumption))
                        .orElse(Constants.POWER_DEFAULT);
        logger.info("min power consumption for year : {} ", minPowerConsumption);
    }

    //2) the average value for the year;
    private static void calculateAveragePowerByYear() {
        double averagePowerConsumption = powerConsumptions
                .stream()
                .mapToLong(PowerConsumption::getPowerConsumption)
                .average()
                .orElse(-1);
        logger.info("average power consumption for year : {} ", averagePowerConsumption);
    }

    // 3) the maximum and minimum values in each month,
    // output to the logger in the format "month name (order is important) - value";
    // 4) the average value in each month,
    // output to the logger in the format "month name (order is important) - value";
    private static void calculateSummaryStatisticsForMonth() {
        Map<Month, LongSummaryStatistics> map =
                powerConsumptions
                        .stream()
                        .collect(groupingBy(x -> x.getPeriod().getMonth(),
                                TreeMap::new,
                                summarizingLong(PowerConsumption::getPowerConsumption)));

        for (Map.Entry<Month, LongSummaryStatistics> entry : map.entrySet()) {
            logger.info("max power consumption for {} : {} ", entry.getKey(), entry.getValue().getMax());
            logger.info("min power consumption for {} : {} ", entry.getKey(), entry.getValue().getMin());
            logger.info("average power consumption for {} : {} ", entry.getKey(), entry.getValue().getAverage());
        }
    }

    //Create a collector that will collect a sorted collection of dates
    //with the maximum value of the numeric field of each month.
    private static void sequentialCollectMaxPowerForMonths() {
        powerConsumptions
                .stream()
                .collect(PowerCollector.toPowerConsumptionList())
                .forEach(power -> {
                    Month month = power.getPeriod().getMonth();
                    logger.info("sequential stream collect max power consumption for {} : {} ", month, power);
                });
    }

    //The collector must be able to work correctly on a parallel stream (also demonstrate).
    private static void parallelCollectMaxPowerForMonths() {
        powerConsumptions
                .parallelStream()
                .collect(PowerCollector.toPowerConsumptionList())
                .forEach(power -> {
                    Month month = power.getPeriod().getMonth();
                    logger.info("parallel stream collect max power consumption for {} : {} ", month, power);
                });
    }
}