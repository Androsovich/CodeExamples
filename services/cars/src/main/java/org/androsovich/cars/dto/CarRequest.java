package org.androsovich.cars.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequest {
    private String vin;
    private String stateNumber;
    private String brand;
    private String manufacturer;
    private LocalDate yearOfManufacture;
}
