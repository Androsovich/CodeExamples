package org.androsovich.applications.dto.bid;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BidRequest {
    private String id;
    private String name;
    private String text;
    private String status;
    private String phone;
}
