package org.androsovich.util;

import org.androsovich.models.Flight;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;


import static java.util.stream.Collectors.*;

public class FlightCalculator {

    private FlightCalculator() {}

    public static Map<String, Duration> getMinFlightTimeForCarrier(List<Flight> flights, String destination, String departure) {
        return flights.stream()
                .filter(flight -> flight.getDeparture().equals(departure))
                .filter(flight -> flight.getDestination().equals(destination))
                .collect(toMap(Flight::getCarrier, Flight::getFlightDuration, BinaryOperator.minBy(Duration::compareTo)));
    }

    public static double getAverageFlights(List<Flight> flights, String destination, String departure) {
        return flights.stream()
                .filter(flight -> flight.getDeparture().equals(departure))
                .filter(flight -> flight.getDestination().equals(destination))
                .mapToInt(Flight::getPrice)
                .average().orElse(0);
    }

    public static double getMedianPriceFlights(List<Flight> flights, String destination, String departure) {
        List<Integer> prices = new ArrayList<>(flights.stream()
                .filter(flight -> flight.getDeparture().equals(departure))
                .filter(flight -> flight.getDestination().equals(destination))
                .map(Flight::getPrice)
                .toList());

        double medianPrice;
        int size = prices.size();

        prices.sort(Comparator.naturalOrder());

        if(size % 2 == 0) {
            medianPrice = (prices.get(size/2 - 1) + prices.get(size/2)) / 2.0;
        }else {
            medianPrice = prices.get(size/2);
        }

        return medianPrice;
    }
}
