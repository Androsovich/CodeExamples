package com.epam.summer.lab.utils;

import java.util.concurrent.ThreadLocalRandom;

public class HelperUtils {
    public static Long randomizer(final long min, final long max) {
        return ThreadLocalRandom
                .current()
                .longs(min, max)
                .findFirst()
                .orElse(min);
    }

    public static Long randomizer() {
        final int DEFAULT_MIN_VALUE = 0;
        final int DEFAULT_MAX_VALUE = 100;
        return randomizer(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    public static void randomizerDelay() {
        final int MIN_DELAY = 0;
        final int MAX_DELAY = 3000;
        try {
            Thread.sleep(randomizer(MIN_DELAY, MAX_DELAY));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
