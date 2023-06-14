package com.epam.summer.lab.cities;

import com.epam.summer.lab.cities.interfaces.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static com.epam.summer.lab.utils.TimeUtils.doSleepMilliSeconds;

public class Spain implements City {
    private static final Logger logger = LoggerFactory.getLogger(Spain.class);

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void startExcursion(int time) {
        final long TOURIST_ID = Thread.currentThread().getId();

        doSleepMilliSeconds();

        try {
            logger.info("tourist [{}] arrived to bitch ", TOURIST_ID);

            countDownLatch.await();

            doSleepMilliSeconds(time);

            logger.info("tourist [{}] rest  time : {} ms ", TOURIST_ID, time);

        } catch (InterruptedException e) {
            logger.error("InterruptedException in {}  : {}",  Spain.class, e);
            e.printStackTrace();
        }
    }

    @Override
    public void actionInExcursion() {
//        doSleepMilliSeconds();

        countDownLatch.countDown();
    }
}