package com.epam.summer.lab.services;

import com.epam.summer.lab.model.Person;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.*;

public interface PersonService {

    void writePersonsToFile(BiConsumer<List<Person>, String> personToFile, String filename);

    List<Person> getRandomPersons(Supplier<List<Person>> personsSupplier);

    List<Person> getWorkablePersons(Predicate<Person> isWorkable);

    List<Person> getSortedPersons(Comparator<Person> comparator);

    List<Person> getPersonsWithConvertedNames(UnaryOperator<Person> personUnaryOperator);

    Map<Boolean, List<Person>> getPartitionPersonsByWorkable(Function<List<Person>, Map<Boolean, List<Person>>>
                                                                     listMapFunction);

    //simple way Comparator.comparing(Person::getName)
    static Comparator<Person> compareByName() {
        return (firstPerson, secondPerson) -> firstPerson.getName().compareTo(secondPerson.getName());
    }

    // simple way Comparator.comparing(Person::getName).thenComparing(Person::getAge);
    static Comparator<Person> compareByNameThenAge() {
        return (firstPerson, secondPerson) -> {
            int result = firstPerson.getName().compareTo(secondPerson.getName());

            if (result == 0) {
                return Integer.compare(firstPerson.getAge(), secondPerson.getAge());
            } else {
                return result;
            }
        };
    }
}