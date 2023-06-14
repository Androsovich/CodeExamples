package com.epam.summer.lab.models;

import com.epam.summer.lab.providers.CityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.epam.summer.lab.utils.Constants.*;
import static com.epam.summer.lab.utils.RandomUtils.randomizerPhoto;
import static com.epam.summer.lab.utils.RandomUtils.randomizerTimeMeal;
import static com.epam.summer.lab.utils.TimeUtils.doSleepMilliSeconds;

public class Tourist implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Tourist.class);

    private final CityProvider cityProvider;
    private final Exchanger<Integer> exchanger;
    private final Phaser phaser;
    private final int timeMeal;
    private final AtomicInteger photo = new AtomicInteger();
    private boolean isInTravel;


    public Tourist(Phaser phaser, CityProvider cityProvider, Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
        this.cityProvider = cityProvider;
        this.phaser = phaser;
        this.isInTravel = true;
        this.timeMeal = randomizerTimeMeal();

        phaser.register();
    }

    @Override
    public Integer call() throws Exception {
        logger.info("Tourist [{}] arrived to bus", Thread.currentThread().getId());

        doSleepMilliSeconds();

        while (isInTravel) {
            visitCity(phaser.getPhase());

            phaser.arriveAndAwaitAdvance();
        }
        phaser.arriveAndDeregister();

        doSleepMilliSeconds();

        logger.info("Tourist [{}] arrived to last station and leave bus", Thread.currentThread().getId());
        return photo.get();
    }

    private void visitCity(int phase) throws InterruptedException {
        doSleepMilliSeconds();

        cityProvider.visitCity(phase, timeMeal);
        doPhoto();

        if (phaser.getPhase() == LAST_STOP_CITY) {
            isInTravel = false;

            Integer checkTransfer = exchanger.exchange(photo.get());
            logger.info("transfer photo from {} completed: result : {}", Thread.currentThread().getName(), checkTransfer);
        }
    }

    private void doPhoto() {
        final int counter = randomizerPhoto();

        for (int i = 0; i < counter; i++) {
            photo.getAndIncrement();
        }
    }
}