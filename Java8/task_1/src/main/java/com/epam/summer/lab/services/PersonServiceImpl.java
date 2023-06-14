package com.epam.summer.lab.services;

import com.epam.summer.lab.model.Person;
import com.epam.summer.lab.utils.GeneratePerson;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class PersonServiceImpl implements PersonService {
    private final List<Person> persons;

    public PersonServiceImpl() {
        this.persons = this.getRandomPersons(GeneratePerson::getListPerson);
    }

    @Override
    public void writePersonsToFile(BiConsumer<List<Person>, String> personsToFile, String filename) {
        personsToFile.accept(persons, filename);
    }

    @Override
    public List<Person> getRandomPersons(Supplier<List<Person>> personsSupplier) {
        return personsSupplier.get();
    }

    @Override
    public List<Person> getWorkablePersons(Predicate<Person> isWorkable) {

        return
                persons
                        .stream()
                        .filter(isWorkable)
                        .collect(Collectors.toList());
    }

    @Override
    public List<Person> getPersonsWithConvertedNames(UnaryOperator<Person> convertName) {
        return
                persons
                        .stream()
                        .map(convertName)
                        .collect(Collectors.toList());
    }

    @Override
    public Map<Boolean, List<Person>> getPartitionPersonsByWorkable(Function<List<Person>, Map<Boolean, List<Person>>>
                                                                            listMapFunction) {
        return listMapFunction.apply(persons);
    }

    @Override
    public List<Person> getSortedPersons(Comparator<Person> personsComparator) {
        return
                persons
                        .stream()
                        .sorted(personsComparator)
                        .collect(Collectors.toList());
    }
}