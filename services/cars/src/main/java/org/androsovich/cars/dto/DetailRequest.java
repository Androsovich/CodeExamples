package org.androsovich.cars.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailRequest {
    private String name;
    private String serialNumber;
}
