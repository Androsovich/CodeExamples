package org.androsovich.models;

import lombok.*;
import org.androsovich.util.DurationUtil;

import java.time.*;

import static org.androsovich.constants.Constants.CITY_ZONE_ID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Flight {
    private String destination;
    private String departure;
    private Duration flightDuration;
    private String carrier;
    private int price;

    public static Flight initialize(Ticket ticket) {
        ZoneId destinationZoneId = CITY_ZONE_ID.get(ticket.getDestination());
        ZoneId departureZoneId = CITY_ZONE_ID.get(ticket.getOrigin());

        LocalDateTime destinationLocalDateTime = ticket.getArrivalDate().atTime(ticket.getArrivalTime());
        LocalDateTime departureLocalDateTime =  ticket.getDepartureDate().atTime(ticket.getDepartureTime());

        ZonedDateTime destinationZonedDateTime = ZonedDateTime.of(destinationLocalDateTime, destinationZoneId);
        ZonedDateTime departureZonedDateTime = ZonedDateTime.of(departureLocalDateTime, departureZoneId);

        Duration duration = Duration.between(departureZonedDateTime, destinationZonedDateTime);

        return new Flight(ticket.getDestination(), ticket.getOrigin(), duration, ticket.getCarrier(), ticket.getPrice());
    }

    @Override
    public String toString() {
        return "Flight[" +
                "destination='" + destination + '\'' +
                ", departure='" + departure + '\'' +
                ", flightDuration=" + DurationUtil.humanReadableFormat(flightDuration) +
                ", carrier='" + carrier + '\'' +
                ", price='" + price + '\'' +
                ']';
    }
}
