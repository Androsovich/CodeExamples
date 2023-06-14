package com.epam.summer.lab.model;

import com.epam.summer.lab.interfaces.Executable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;

import static com.epam.summer.lab.utils.GeneratePerson.*;

@Getter
@AllArgsConstructor
@ToString(exclude = "initialization")
public class Person {
    private final Executable initialization = this::initPerson;

    private String name;
    private int age;
    private LocalDate birthday;

    public Person() {
        initialization.execute();
    }

    private int calculateAge(LocalDate birthday) {
        final int birthdayYear = birthday.getYear();
        return LocalDate
                .now()
                .minusYears(birthdayYear)
                .getYear();
    }

    private void initPerson() {
        this.name = randomName();
        this.birthday = randomBirthday();
        this.age = this.calculateAge(this.birthday);
    }

    public Person newPersonWithNameToUpperCase() {
        return new Person(this.name.toUpperCase(), this.age, this.birthday);
    }
}