package com.epam.summer.lab.handler;

import com.epam.summer.lab.annotations.ThisCodeSmells;
import com.epam.summer.lab.annotations.ThisCodeSmellsContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThisCodeSmellsAnnotationHandler {
    private final Class<? extends Annotation> annotationClass;
    private final Class<? extends Annotation> annotationClassContainer;
    private List<String> names;
    private String frequentReviewer;

    public ThisCodeSmellsAnnotationHandler(Class<? extends Annotation> annotationClass,
                                           Class<? extends Annotation> annotationClassContainer) {
        this.annotationClass = annotationClass;
        this.annotationClassContainer = annotationClassContainer;
    }

    public List<String> getNames() {
        return names;
    }

    public String getFrequentReviewer() {
        return frequentReviewer;
    }

    public void handle(final Object object) {
        final String DELIMITER = " : ";
        final Class<?> aClass = object.getClass();

        Map<String, Long> mapReviewers = frequencyMap(getResultReviewers(aClass));
        final String result = getFrequentReviewer(mapReviewers);

        this.names = getNamesWhereUseAnnotation(aClass);
        this.frequentReviewer = String.join(DELIMITER, result, mapReviewers.get(result).toString());
    }

    private String getFrequentReviewer(Map<String, Long> mapReviewers) {
        return Collections
                .max(mapReviewers.entrySet(), Map.Entry.comparingByValue())
                .getKey();
    }

    private List<String> getNamesWhereUseAnnotation(final Class<?> clazz) {
        Stream<String> names = Stream
                .concat(getNamesElements(clazz::getDeclaredMethods),
                        getNamesElements(clazz::getDeclaredFields));

        Stream<String> className = Stream.of(clazz.getName());

        Stream<String> result = isAnnotationsPresent(clazz) ? Stream.concat(className, names) : names;
        return result.collect(Collectors.toList());
    }

    private <E extends AccessibleObject> Stream<E> getElementsIsAnnotationsPresent(final Supplier<E[]> supplier) {
        return Arrays.stream(supplier.get()).filter(this::isAnnotationsPresent);
    }

    private <E extends AccessibleObject> Stream<String> getNamesElements(final Supplier<E[]> supplier) {
        return getElementsIsAnnotationsPresent(supplier).map(E::toString);
    }

    private <E extends AccessibleObject> Stream<Annotation[]> getAnnotations(final Supplier<E[]> supplier) {
        return getElementsIsAnnotationsPresent(supplier).map(E::getDeclaredAnnotations);
    }

    private Stream<String> getReviewersFromElements(final Class<?> clazz) {
        return Stream
                .concat(getAnnotations(clazz::getDeclaredFields),
                        getAnnotations(clazz::getDeclaredMethods))
                .flatMap(this::getReviewersFromAnnotations);
    }

    private Stream<String> getReviewersFromClass(final Class<?> clazz) {
        return isAnnotationsPresent(clazz) ? getReviewersFromAnnotations(clazz.getAnnotations()) : Stream.empty();
    }

    private Stream<String> getReviewersFromAnnotations(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .map(this::parseAnnotation)
                .flatMap(List::stream);
    }
    
    private Stream<String> getResultReviewers(final Class<?> clazz) {
        return Stream.concat(getReviewersFromClass(clazz), getReviewersFromElements(clazz));
    }

    private List<String> parseAnnotation(Annotation annotation) {
        final List<String> reviewers = new ArrayList<>();

        if (annotation.annotationType() == this.annotationClass) {
            final String reviewer = ((ThisCodeSmells) annotation).reviewer();
            reviewers.add(reviewer);
        } else if (annotation.annotationType() == this.annotationClassContainer) {
            reviewers.addAll(getValuesFromContainer(annotation));
        }
        return reviewers;
    }

    private List<String> getValuesFromContainer(Annotation annotation) {
        return Arrays.stream(((ThisCodeSmellsContainer) annotation).value())
                .map(ThisCodeSmells::reviewer)
                .collect(Collectors.toList());
    }

    private <T extends AnnotatedElement> boolean isAnnotationsPresent(T t) {
        return t.isAnnotationPresent(annotationClass) || t.isAnnotationPresent(annotationClassContainer);
    }

    private <T> Map<T, Long> frequencyMap(Stream<T> elements) {
        return elements.collect(
                Collectors.groupingBy(
                        Function.identity(),
                        HashMap::new,
                        Collectors.counting()
                )
        );
    }
}