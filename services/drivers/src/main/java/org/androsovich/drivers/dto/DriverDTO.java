package org.androsovich.drivers.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO extends RepresentationModel<DriverDTO> {
    private Long id;
    private String fullName;
    private String passport;
    private String driverLicense;
    private int experience;
}