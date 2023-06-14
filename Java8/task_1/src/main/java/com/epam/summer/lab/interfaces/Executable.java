package com.epam.summer.lab.interfaces;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public interface Executable {
    Logger logger = LoggerFactory.getLogger(Executable.class);

    void execute();

    default <T> void printToLog(T type) {
        logger.info("execute  : {} ", type);
    }

    static void writeToFile(Object object, String fileName) {
        final String NEW_LINE = "\n";
        final File file = new File(fileName);

        try {
            FileUtils.writeStringToFile(file, object.toString(), StandardCharsets.UTF_8, true);
            FileUtils.writeStringToFile(file, NEW_LINE, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            logger.error("unable to write to file {} : Exception : ", file, e);
            e.printStackTrace();
        }
    }
}