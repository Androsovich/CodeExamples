package com.epam.summer.lab.utils;

import java.util.*;
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
        final int DEFAULT_MIN_VALUE = 1;
        final int DEFAULT_MAX_VALUE = 5;
        return randomizer(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    private static <E> List<E> pickNRandomElements(List<E> list, int n, Random random) {
        int length = list.size();

        if (length < n) return null;

        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i, random.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n) {
        return new ArrayList<>(Objects.requireNonNull(pickNRandomElements(list, n, ThreadLocalRandom.current())));
    }
}