package com.epam.summer.lab;

import com.epam.summer.lab.interfaces.Executable;
import com.epam.summer.lab.model.Person;
import com.epam.summer.lab.services.PersonService;
import com.epam.summer.lab.services.PersonServiceImpl;
import com.epam.summer.lab.utils.GeneratePerson;
import com.epam.summer.lab.utils.HelperUtils;

import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

import static com.epam.summer.lab.constants.Constants.*;

/**
 * first implementation Executable - in class Person( initialization Person)
 * second implementation Executable - runner( run new Thread)
 * third implementation Executable - delay(random delay start application)
 * <p>
 * default method Executable - print to log;
 * static method Executable - write to file, using in BiConsumer
 * The MainClass class describes six implementations of functional interfaces
 * (Runnable - runnable_application,
 * Supplier - listSupplier,
 * BiConsumer - listStringBiConsumer,
 * UnaryOperation - unaryOperator,
 * Function - listMapFunction,
 * Predicate - isWorkable)
 */
public class MainClass {
    private final static PersonService personService = new PersonServiceImpl();

    private final static Runnable runnable_application = MainClass::runApplication;
    private final static Executable delay = HelperUtils::randomizerDelay;
    private final static Executable runner = new Thread(runnable_application)::start;


    public static void main(String[] args) {
        runner.execute();
    }

    private static void runApplication() {
        final Predicate<Person> isWorkable = person -> person.getAge() >= WORKABLE_AGE;
        final Supplier<List<Person>> listSupplier = GeneratePerson::getListPerson;
        final BiConsumer<List<Person>, String> listStringBiConsumer = Executable::writeToFile;
        final UnaryOperator<Person> unaryOperator = Person::newPersonWithNameToUpperCase;

        final Function<List<Person>, Map<Boolean, List<Person>>> listMapFunction = persons ->
                persons
                        .stream()
                        .collect(Collectors.partitioningBy(isWorkable));

        delay.execute();

        usingPersonServiceSimpleComparator();
        usingPersonServiceCascadeComparator();
        usingPersonServicePredicate(isWorkable);
        usingPersonServiceUnaryOperation(unaryOperator);
        usingPersonServiceFunction(listMapFunction);
        usingPersonServiceSupplier(listSupplier);
        usingPersonServiceBiConsumer(listStringBiConsumer);
    }

    private static void usingPersonServicePredicate(Predicate<Person> isWorkable) {
        runner.printToLog(MESSAGE_USE_PREDICATE);
        personService
                .getWorkablePersons(isWorkable)
                //print to log the result of the predicate
                .forEach(runner::printToLog);
    }

    private static void usingPersonServiceSimpleComparator() {
        runner.printToLog(MESSAGE_SIMPLE_COMPARATOR);
        personService
                .getSortedPersons(PersonService.compareByName())
                //print to log the result of the comparator
                .forEach(runner::printToLog);
    }

    private static void usingPersonServiceCascadeComparator() {
        runner.printToLog(MESSAGE_CASCADE_COMPARATOR);
        personService
                .getSortedPersons(PersonService.compareByNameThenAge())
                //print to log the result of the comparator
                .forEach(runner::printToLog);
    }

    private static void usingPersonServiceUnaryOperation(UnaryOperator<Person> unaryOperator) {
        runner.printToLog(MESSAGE_USE_UNARY_OPERATION);
        personService
                .getPersonsWithConvertedNames(unaryOperator)
                //print to log the result of the unary operation
                .forEach(runner::printToLog);
    }

    private static void usingPersonServiceFunction(Function<List<Person>, Map<Boolean, List<Person>>> listMapFunction) {
        runner.printToLog(MESSAGE_USE_FUNCTION);
        personService
                .getPartitionPersonsByWorkable(listMapFunction)
                //print to log the result of the function
                .forEach((key, value) -> {
                    runner.printToLog(key);
                    runner.printToLog(value);
                });
    }

    private static void usingPersonServiceSupplier(Supplier<List<Person>> listSupplier) {
        runner.printToLog(MESSAGE_USE_SUPPLIER);
        personService
                .getRandomPersons(listSupplier)
                //print to log the result of the Supplier
                .forEach(runner::printToLog);
    }

    private static void usingPersonServiceBiConsumer(BiConsumer<List<Person>, String> listStringBiConsumer) {
        personService.writePersonsToFile(listStringBiConsumer, FILE_NAME);
    }
}