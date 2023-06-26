package org.androsovich.cars.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailResponse extends RepresentationModel<DetailResponse> {
    private Long id;
    private String name;
    private String serialNumber;
    private Long carId;
}
