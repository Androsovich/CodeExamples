package com.epam.summer.lab.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileUtility {
    private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

    public static String[] readResourceFile(final String fileName) {
        final String delimiter = ";";

        ClassLoader classLoader = RandomUtils.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        String resultString = null;
        try {
            resultString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("IOException in {} : IOException : {}", FileUtility.class, e);
            e.printStackTrace();
        }
        return Objects.requireNonNull(resultString).split(delimiter);
    }
}