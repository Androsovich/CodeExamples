package org.androsovich.cars.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.androsovich.cars.constatns.CommonConstants.*;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
public class Car extends BaseEntity {

    public Car(String vin, String stateNumber) {
        this.vin = vin;
        this.stateNumber = stateNumber;
    }

    @Column(name = "vin", nullable = false)
    @Size(min = 2, max = 25)
    private String vin;

    @Column(name = "state_number", nullable = false)
    @Size(min = 2, max = 25)
    private String stateNumber;

    @Basic
    private String brand;

    @Basic
    private String manufacturer;

    @Basic
    @PastOrPresent
    private LocalDate yearOfManufacture;

    @Column(name = "driver_id")
    private Long driverId;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Detail> details = new ArrayList<>();

    @Override
    public String toString() {
        return LEFT_BRACKET + vin + DELIMITER + stateNumber + RIGHT_BRACKET;
    }
}
