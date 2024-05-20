package org.androsovich;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.androsovich.json.JsonParser;
import org.androsovich.json.adapters.LocalTimeTypeAdapter;
import org.androsovich.json.adapters.LocalDateTypeAdapter;
import org.androsovich.models.Flight;
import org.androsovich.models.Ticket;
import org.androsovich.util.DurationUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.androsovich.constants.Constants.TEL_AVIV;
import static org.androsovich.constants.Constants.VLADIVOSTOK;
import static org.androsovich.util.FlightCalculator.*;


public class Main {

    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

        JsonParser parser = new JsonParser(gson);
        List<Ticket> tickets = parser.parse(args[0]);

        List<Flight> flights = tickets.stream().map(Flight::initialize).toList();

        Map<String, Duration> minFlightDurationsCarriers = getMinFlightTimeForCarrier(flights, TEL_AVIV, VLADIVOSTOK);
        double average = getAverageFlights(flights, TEL_AVIV, VLADIVOSTOK);
        double median = getMedianPriceFlights(flights, TEL_AVIV, VLADIVOSTOK);

        minFlightDurationsCarriers.forEach((key, value) -> System.out.println(key + ":" + DurationUtil.humanReadableFormat(value)));
        System.out.println("average flight between vladivostok and tel_aviv  = " + average);
        System.out.println("median flight between vladivostok and tel_aviv  = " + median);
        System.out.println("difference between median and average  = " + (average - median));
    }
}