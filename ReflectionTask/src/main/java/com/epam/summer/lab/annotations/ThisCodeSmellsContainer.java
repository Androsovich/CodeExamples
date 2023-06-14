package com.epam.summer.lab.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD,
        ElementType.TYPE, ElementType.PARAMETER,
        ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE,
        ElementType.LOCAL_VARIABLE, ElementType.PACKAGE,
        ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
public @interface ThisCodeSmellsContainer {

    ThisCodeSmells[] value();
}