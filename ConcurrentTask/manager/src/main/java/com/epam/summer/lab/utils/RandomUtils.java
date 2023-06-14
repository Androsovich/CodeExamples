package com.epam.summer.lab.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static int randomizerTimeMeal() {
        final int MIN_TIME_MEAL = 1000;
        final int MAX_TIME_MEAL = 5000;
        return
                ThreadLocalRandom
                        .current()
                        .ints(MIN_TIME_MEAL, MAX_TIME_MEAL)
                        .findFirst()
                        .orElse(MIN_TIME_MEAL);
    }

    public static int randomizerPhoto() {
        final int MIN = 1;
        final int MAX = 5;
        return
                ThreadLocalRandom
                        .current()
                        .ints(MIN, MAX)
                        .findFirst()
                        .orElse(MIN);
    }
}
