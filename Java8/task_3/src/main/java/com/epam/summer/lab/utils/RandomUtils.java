package com.epam.summer.lab.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    public static Integer randomizer(final int min, final int max) {
        return ThreadLocalRandom
                .current()
                .ints(min, max)
                .findFirst()
                .orElse(min);
    }

    public static Integer randomizer() {
        final int DEFAULT_MIN_VALUE = 1000;
        final int DEFAULT_MAX_VALUE = 100000;
        return randomizer(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }
}