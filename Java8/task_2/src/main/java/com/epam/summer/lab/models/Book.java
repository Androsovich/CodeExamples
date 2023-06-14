package com.epam.summer.lab.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.summer.lab.constants.Constants.MAX_NUMBER_PAGES;
import static com.epam.summer.lab.constants.Constants.MIN_NUMBER_PAGES;
import static com.epam.summer.lab.utils.RandomUtils.randomizer;

@AllArgsConstructor
@Getter
@Setter
public class Book {
    private final String name;
    private final int numberPages;
    private List<Author> authors;

    public Book(String name) {
        this.name = name;
        this.numberPages = randomizer(MIN_NUMBER_PAGES, MAX_NUMBER_PAGES);
        this.authors = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Book{" + name + "; numberPages=" + numberPages + ";" + authorsNames().toString() + "}";
    }

    private List<String> authorsNames() {
        return authors
                .stream()
                .map(Author::getName)
                .collect(Collectors.toList());
    }
}