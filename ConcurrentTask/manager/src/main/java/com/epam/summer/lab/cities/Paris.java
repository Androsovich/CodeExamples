package com.epam.summer.lab.cities;

import com.epam.summer.lab.cities.interfaces.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import static com.epam.summer.lab.utils.Constants.NUMBER_TOURIST_WAIT;
import static com.epam.summer.lab.utils.TimeUtils.doSleepMilliSeconds;

public class Paris implements City {
    private static final Logger logger = LoggerFactory.getLogger(Paris.class);

    private final Semaphore entranceGallery = new Semaphore(NUMBER_TOURIST_WAIT, true);//bad name
    private final Semaphore exitGallery = new Semaphore(0);
    private final AtomicInteger numberTourists = new AtomicInteger();

    @Override
    public void startExcursion(int time) {
        final long TOURIST_ID = Thread.currentThread().getId();

        visitLouvre(TOURIST_ID);
    }

    private void visitLouvre(final long touristId) {
        //delay only for logger print
        doSleepMilliSeconds();

        logger.info("tourist [{}] goes to Louvre", touristId);

        //delay only for logger print
        doSleepMilliSeconds();

        try {
            activityTouristsNearPicture(touristId);

            logger.info("tourist [{}] got out of the Louvre and got on the bus : queue threads - {}", touristId,
                    entranceGallery.getQueueLength());
        } catch (InterruptedException e) {
            logger.error("InterruptedException in {}  : {}", Paris.class, e);
            e.printStackTrace();
        }
    }

    private void activityTouristsNearPicture(final long touristId) throws InterruptedException {
        entranceGallery.acquire();
        numberTourists.incrementAndGet();

        logger.info("next tourist [{}] goes to the picture", touristId);

        //imitation of the arrival of a tourist to the picture
        doSleepMilliSeconds();

        logger.info("tourist [{}] see the Gioconda : tourists near picture - {}", touristId, numberTourists.get());

        exitGallery.acquire();
        numberTourists.decrementAndGet();
    }

    @Override
    public void actionInExcursion() {

        // manager delay for checking the semaphores
        doSleepMilliSeconds();

        if (entranceGallery.availablePermits() == 0 && numberTourists.get() < NUMBER_TOURIST_WAIT) {
            entranceGallery.release();
        }

        if (exitGallery.availablePermits() == 0) {

            //the delay of tourists near the picture
            doSleepMilliSeconds();

            exitGallery.release();
        }
    }
}