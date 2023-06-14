package com.epam.summer.lab.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ThisCodeSmellsContainer.class)
@Target({ElementType.FIELD, ElementType.METHOD,
        ElementType.TYPE, ElementType.PARAMETER,
        ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE,
        ElementType.LOCAL_VARIABLE, ElementType.PACKAGE,
        ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
public @interface ThisCodeSmells {

    String reviewer();
}