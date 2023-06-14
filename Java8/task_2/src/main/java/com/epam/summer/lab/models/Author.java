package com.epam.summer.lab.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.summer.lab.constants.Constants.MAX_AGE;
import static com.epam.summer.lab.constants.Constants.MIN_AGE;
import static com.epam.summer.lab.utils.RandomUtils.randomizer;

@AllArgsConstructor
@Getter
@Setter
public class Author {
    private final String name;
    private final int age;
    private List<Book> books;

    public Author(String name) {
        this.name = name;
        this.age = randomizer(MIN_AGE, MAX_AGE);
        this.books = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Author{" + name + ";" + age + ";" + booksNames().toString() +  "}";
    }

    private List<String> booksNames() {
        return books
                .stream()
                .map(Book::getName)
                .collect(Collectors.toList());
    }
}