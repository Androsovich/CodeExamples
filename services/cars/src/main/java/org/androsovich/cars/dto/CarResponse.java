package org.androsovich.cars.dto;

import lombok.*;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponse extends RepresentationModel<CarResponse> {
    private Long id;
    private String vin;
    private String stateNumber;
    private String brand;
    private String manufacturer;
    private LocalDate yearOfManufacture;
    private Long driverId;
}