package com.epam.summer.lab;

import com.epam.summer.lab.annotations.ThisCodeSmells;
import com.epam.summer.lab.annotations.ThisCodeSmellsContainer;
import com.epam.summer.lab.handler.ThisCodeSmellsAnnotationHandler;
import com.epam.summer.lab.models.Person;

public class MainClass {
    public static void main(String[] args) {
        ThisCodeSmellsAnnotationHandler handler = new ThisCodeSmellsAnnotationHandler(ThisCodeSmells.class,
                ThisCodeSmellsContainer.class);
        handler.handle(new Person("Rikki", "Tikki Tavi", "Africa"));
        printResults(handler);

    }

    private static void printResults(ThisCodeSmellsAnnotationHandler handler) {
        final String PRINT_DELIMITER = "#########################################################";

        handler.getNames().forEach(System.out::println);
        System.out.println(PRINT_DELIMITER);
        System.out.println(handler.getFrequentReviewer());
    }
}
