package com.epam.summer.lab.cities;

import com.epam.summer.lab.cities.interfaces.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static com.epam.summer.lab.utils.Constants.MAX_NUMBER_TOURIST;
import static com.epam.summer.lab.utils.Constants.NUMBER_TOURIST_WAIT;
import static com.epam.summer.lab.utils.TimeUtils.doSleepMilliSeconds;

public class Rome implements City {
    private static final Logger logger = LoggerFactory.getLogger(Rome.class);

    private final CyclicBarrier pizzaWaiter = new CyclicBarrier(NUMBER_TOURIST_WAIT, this::pizzaWaiterService);
    private final CyclicBarrier pastaWaiter = new CyclicBarrier(NUMBER_TOURIST_WAIT, this::pastaWaiterService);
    private final Semaphore pizzaSemaphore = new Semaphore(NUMBER_TOURIST_WAIT);
    private final Semaphore pastaSemaphore = new Semaphore(NUMBER_TOURIST_WAIT);
    private final ConcurrentLinkedDeque<String> restaurants = new ConcurrentLinkedDeque<>();

    private int portionCounter;

    private void pizzaWaiterService() {
        logger.info("########################## Service tourists in a restaurant Pizza ##############################");
    }

    private void pastaWaiterService() {
        logger.info(" ######################### Service tourists in a restaurant Pasta ##############################");
    }

    @Override
    public void startExcursion(int timeMeal) {
        final long TOURIST_ID = Thread.currentThread().getId();

        doSleepMilliSeconds();

        try {
            if (getNumberTourist() % 2 == 0) {
                visitRestaurantPizza(timeMeal, TOURIST_ID);
            } else {
                visitRestaurantPasta(timeMeal, TOURIST_ID);
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException  in {} : {}", Rome.class, e);
            e.printStackTrace();
        }
    }

    @Override
    public void actionInExcursion() {

        while (portionCounter < MAX_NUMBER_TOURIST) {
            if (portionCounter % 2 == 0) {
                restaurants.addFirst("Pizza [" + portionCounter + "]");
            } else {
                restaurants.addLast("Pasta [" + portionCounter + "]");
            }
            portionCounter++;
        }
    }

    private void visitRestaurantPizza(int timeMeal, long touristId) throws InterruptedException {
        logger.info("tourist [{}] goes to the restaurant (Pizza)", touristId);

        waitInRestaurant(pizzaSemaphore, pizzaWaiter);

        startServicePizza(timeMeal, touristId);
    }

    private void visitRestaurantPasta(int timeMeal, long touristId) throws InterruptedException {
        logger.info("tourist [{}] goes to the restaurant (Pasta)", touristId);

        waitInRestaurant(pastaSemaphore, pastaWaiter);

        startServicePasta(timeMeal, touristId);
    }

    private void waitInRestaurant(Semaphore semaphore, CyclicBarrier waiter) throws InterruptedException {
        doSleepMilliSeconds();

        semaphore.acquire();

        try {
            waiter.await();

        } catch (BrokenBarrierException e) {
            logger.error("BrokenBarrierException  in {} : {}", Rome.class, e);
            e.printStackTrace();
        }
    }

    private void startServicePizza(int timeMeal, long touristId) {
        final String pizza = restaurants.pollFirst();

        logger.info("tourist [{}] started eating {} in a restaurant (Pizza)", touristId, pizza);

        startTouristEating(pizzaSemaphore, timeMeal, pizza, touristId);
    }

    private void startServicePasta(int timeMeal, long touristId) {
        final String pasta = restaurants.pollLast();

        logger.info("tourist [{}] started eating {} in a restaurant (Pasta)", touristId, pasta);

        startTouristEating(pastaSemaphore, timeMeal, pasta, touristId);
    }

    private void startTouristEating(Semaphore semaphore, int timeMeal, String dish, long touristId) {
        doSleepMilliSeconds(timeMeal);

        logger.info("tourist [{}]  ate {} and got on the bus : meal time {} ms ", touristId, dish, timeMeal);

        semaphore.release();
    }

    //an alternative to create a ThreadFactory implementation and named each thread
    private int getNumberTourist() {
        final char DELIMITER = '-';

        String touristName = Thread.currentThread().getName();
        int delimiterLastIndex = touristName.lastIndexOf(DELIMITER);
        String numberTourist = touristName.substring(delimiterLastIndex + 1);

        return Integer.parseInt(numberTourist);
    }
}