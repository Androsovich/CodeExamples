package com.epam.summer.lab.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    public static void doSleepMilliSeconds(int timeOut) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeOut);
        } catch (InterruptedException e) {
            logger.error("InterruptedException in {} ", TimeUtils.class, e);
            e.printStackTrace();
        }
    }

    public static void doSleepMilliSeconds() {
        final int DEFAULT_TIMEOUT = 1000;//&
        doSleepMilliSeconds(DEFAULT_TIMEOUT);
    }
}