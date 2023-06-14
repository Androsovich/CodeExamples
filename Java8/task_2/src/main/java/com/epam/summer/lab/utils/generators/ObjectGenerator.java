package com.epam.summer.lab.utils.generators;

import com.epam.summer.lab.utils.FileUtility;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectGenerator {

    public static <T> List<T> getListObjects(String filename, Function<String, T> createObject) {
        return Arrays
                .stream(FileUtility.readResourceFile(filename))
                .map(createObject)
                .collect(Collectors.toList());
    }
}