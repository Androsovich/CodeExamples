package com.epam.summer.lab;

import com.epam.summer.lab.models.Tourist;
import com.epam.summer.lab.providers.CityProvider;
import com.epam.summer.lab.managers.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.summer.lab.utils.Constants.MAX_NUMBER_TOURIST;

/**
 * Description:
 * At the start, the tourist  pool thread and the thread Manager are launched.
 * The manager is responsible for creating a separate thread for each city ( as a producer, etc..)
 * Each thread corresponds to the number of a certain phase.Common resources are cities,
 * each city also corresponds to a certain phase. The provider of cities is the CityProvider.
 * To transfer photos to the cloud, I did not make a separate class, instead,
 * the last thread for Spain simulates the work of transferring photos to the cloud via Exchanger
 **/
public class MainClass {
    private static final Logger logger = LoggerFactory.getLogger(MainClass.class);

    public static void main(String[] args) {
        startTouristRoutes();
    }

    private static List<Tourist> arrivedTourists(Phaser phaser, CityProvider cityProvider, Exchanger<Integer> ex) {
        return Stream.generate(() -> new Tourist(phaser, cityProvider, ex))
                .limit(MAX_NUMBER_TOURIST)
                .collect(Collectors.toList());
    }

    private static void startTouristRoutes() {
        final int NUMBER_TOURIST_ROUTES = 2;
        final Exchanger<Integer> exchanger = new Exchanger<>();
        final ExecutorService managerRunner = Executors.newSingleThreadExecutor();
        final ExecutorService excursion = Executors.newFixedThreadPool(MAX_NUMBER_TOURIST);

        try {
            for (int i = 0; i < NUMBER_TOURIST_ROUTES; i++) {
                final CityProvider cityProvider = new CityProvider();
                final Phaser bus = new Phaser();

                Manager manager = new Manager(cityProvider, bus, exchanger);
                List<Tourist> tourists = arrivedTourists(bus, cityProvider, exchanger);
                logger.info("--------------------------------------------------------------------------");
                logger.info("start tour");
                logger.info("--------------------------------------------------------------------------");
                managerRunner.submit(manager);
                excursion.invokeAll(tourists);
            }
            managerRunner.shutdown();
            excursion.shutdown();
        } catch (InterruptedException e) {
            logger.error("InterruptedException| ExecutionException  in {}  : {}",  MainClass.class, e);
            e.printStackTrace();
        }
    }
}