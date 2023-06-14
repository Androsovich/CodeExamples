package com.epam.summer.lab.constants;

import java.time.LocalDate;

public class Constants {
    public static final int MAX_PERSON = 15;
    public static final int WORKABLE_AGE = 18;
    public static final String[] NAMES = {
            "James", "John", "Robert", "Michael", "William", "David",
            "Richard", "Charles", "Joseph", "Thomas", "Mary", "Patricia",
            "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret",
            "Dorothy"};

    public final static String DELIMITER = "###################";
    public final static String MESSAGE_CASCADE_COMPARATOR = createMessage(" Using cascade comparator ");
    public final static String MESSAGE_SIMPLE_COMPARATOR = createMessage(" Using simple comparator ");
    public final static String MESSAGE_USE_PREDICATE = createMessage(" Using predicate ( view workable persons) ");
    public final static String MESSAGE_USE_UNARY_OPERATION = createMessage(" Using unary operation (persons name upperCase) ");
    public final static String MESSAGE_USE_FUNCTION = createMessage(" Using function ( Partition persons by workable ) ");
    public final static String MESSAGE_USE_SUPPLIER = createMessage(" Using supplier ( Create random persons list ) ");

    public final static String FILE_NAME = "persons.txt";

    public static long MIN_DAY = LocalDate.of(1980, 1, 1).toEpochDay();
    public static long MAX_DAY = LocalDate.of(2021, 12, 31).toEpochDay();

    private static String createMessage(String message){
        return DELIMITER + message + DELIMITER;
    }
}