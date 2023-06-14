package com.epam.summer.lab.utils;

import com.epam.summer.lab.model.Person;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.summer.lab.constants.Constants.*;
import static com.epam.summer.lab.utils.HelperUtils.randomizer;

public class GeneratePerson {

    public static List<Person> getListPerson() {
        return Stream
                .generate(Person::new)
                .limit(MAX_PERSON)
                .collect(Collectors.toList());
    }

    public static String randomName() {
        final String DEFAULT_NAME = "VASYA";
        final int START_INDEX = 0;
        return Arrays
                .stream(NAMES)
                .skip(randomizer(START_INDEX, NAMES.length))
                .findFirst()
                .orElse(DEFAULT_NAME);
    }

    public static LocalDate randomBirthday() {
        return LocalDate.ofEpochDay(randomizer(MIN_DAY, MAX_DAY));
    }
}