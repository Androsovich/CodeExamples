package com.epam.summer.lab.managers;

import com.epam.summer.lab.cities.interfaces.City;
import com.epam.summer.lab.providers.CityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static com.epam.summer.lab.utils.Constants.LAST_STOP_CITY;
import static com.epam.summer.lab.utils.TimeUtils.doSleepMilliSeconds;

public class Manager implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Manager.class);

    private final Phaser phaser;
    private final CityProvider provider;
    private final Exchanger<Integer> exchanger;
    private final List<Runnable> threadsActionInCity = new ArrayList<>();

    private int totalPhotoTourists;
    private boolean isProcessing;

    public Manager(CityProvider provider, Phaser phaser, Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
        this.provider = provider;
        this.phaser = phaser;
        this.isProcessing = true;

        phaser.register();
    }

    @Override
    public Integer call() {
        initThreadsForActionInCity();

        while (isProcessing) {
            runActionInCity(phaser);

            doSleepMilliSeconds();
        }

        return totalPhotoTourists;
    }

    /**
     * this method does the main work, it takes a thread from the list
     * to perform actions in the city according to the current phase (city)
     */
    private void runActionInCity(final Phaser phaser) {
        final int indexThreadActionInCity = phaser.getPhase();

        if (indexThreadActionInCity < threadsActionInCity.size()) {

            new Thread(threadsActionInCity.get(indexThreadActionInCity)).start();

            phaser.arriveAndAwaitAdvance();

        } else {
            isProcessing = false;

            phaser.arriveAndDeregister();
        }
    }

    /**
     * this method is used in the Consumer<Integer> actionInPhase
     * action in the phase(city) and serves to operate the method doAction
     */
    private void choiceActionInPhase(Integer phase) {
        doActionInExcursion(phase);

        if (phase == LAST_STOP_CITY) {
            getPhotosFromTourist();
        }
    }

    /**
     * @param actionInPhase - this is the consumer that accepts by default
     *                      method choiceActionInPhase(Integer phase)
     */
    private void doAction(final Consumer<Integer> actionInPhase) {
        final int destination = phaser.getPhase();

        while (phaser.getPhase() == destination) {
            actionInPhase.accept(destination);
        }
    }

    /**
     * this method execute action in city
     */
    private void doActionInExcursion(final Integer destination) {
        final City city = provider.getCities().get(destination);
        city.actionInExcursion();
    }

    private void getPhotosFromTourist() {
        int buffer = 0;

        try {
            buffer = exchanger.exchange(null);
            totalPhotoTourists += buffer;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("transfer : number of photos : {} ", buffer);
    }

    private void initThreadsForActionInCity() {
        threadsActionInCity.add(() -> {
            doSleepMilliSeconds();
            logger.info("---------------------------first stop Prague----------------------------");
            doAction(this::choiceActionInPhase);
        });

        threadsActionInCity.add(() -> {
            logger.info("--------------------second stop Paris-------------------------------------");
            doSleepMilliSeconds();
            doAction(this::choiceActionInPhase);
        });

        threadsActionInCity.add(() -> {
            logger.info("---------------------third stop Rome-------------------------------------");
            doSleepMilliSeconds();
            doAction(this::choiceActionInPhase);
        });

        threadsActionInCity.add(() -> {
            logger.info("------------------------fourth stop Spain---------------------------------");
            doSleepMilliSeconds();
            doAction(this::choiceActionInPhase);

            logger.info("################ Total photo tourists  in Cloud : {}  #############", totalPhotoTourists);
        });
    }
}