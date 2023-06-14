package com.epam.summer.lab.cities;

import com.epam.summer.lab.cities.interfaces.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static com.epam.summer.lab.utils.Constants.MAX_NUMBER_TOURIST;
import static com.epam.summer.lab.utils.TimeUtils.doSleepMilliSeconds;

public class Prague implements City {
    private static final Logger logger = LoggerFactory.getLogger(Prague.class);

    private final BlockingQueue<String> brewer = new SynchronousQueue<>();
    private int touristCounter = MAX_NUMBER_TOURIST;

    @Override
    public void startExcursion(final int timeMeal) {
        final long TOURIST_ID = Thread.currentThread().getId();

        try {
            final String beer = brewer.take();

            logger.info("tourist [{}]  took a beer and started drinking", TOURIST_ID);

            doSleepMilliSeconds(timeMeal);

            logger.info("tourist [{}] drank {} : Time meal : {} ms", TOURIST_ID, beer, timeMeal);
        } catch (InterruptedException e) {
            logger.error("InterruptedException in {} ", Prague.class, e);
            e.printStackTrace();
        }
    }

    @Override
    public void actionInExcursion() {
        final String DRINK = "dark Czech beer";

        doSleepMilliSeconds();

        if (touristCounter > 0) {
            try {
                brewer.put(DRINK);

                logger.info("brewer put the beer");
            } catch (InterruptedException e) {
                logger.error("InterruptedException in {} : {}", Prague.class, e);
                e.printStackTrace();
            }

            touristCounter--;
        }
    }
}