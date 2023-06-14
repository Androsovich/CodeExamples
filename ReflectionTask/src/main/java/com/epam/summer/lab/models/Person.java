package com.epam.summer.lab.models;

import com.epam.summer.lab.annotations.ThisCodeSmells;

@ThisCodeSmells(reviewer = "Peter")
public class Person {
    @ThisCodeSmells(reviewer = "Innocent X")
    private static final int VERSION = 4342342;

    @ThisCodeSmells(reviewer = "Peter")
    @ThisCodeSmells(reviewer = "ALEX")
    public static String address;
    public static String phone;

    @ThisCodeSmells(reviewer = "ALEX")
    private String name;
    private String secondName;

    public Person(String name, String secondName, String address) {
        this.name = name;
        this.secondName = secondName;
        Person.address = address;
    }

    @ThisCodeSmells(reviewer = "Peter")
    @ThisCodeSmells(reviewer = "ALEX")
    public String getName() {
        return name;
    }

    @ThisCodeSmells(reviewer = "Innocent X")
    @ThisCodeSmells(reviewer = "Peter")
    private String getFullName() {
        final String DELIMITER = " ";
        return String.join(DELIMITER, name, secondName);
    }

    @ThisCodeSmells(reviewer = "ALEX")
    public static int getVersion() {
        return changeVersion();
    }

    @ThisCodeSmells(reviewer = "ALEX")
    @ThisCodeSmells(reviewer = "Innocent X")
    private static int changeVersion() {
        final int SEED = 5000;
        return VERSION + SEED;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void doNothing() {
        System.out.println("do nothing");
    }

    public static void doNothingAgain() {
        System.out.println("do nothing again");
    }
}