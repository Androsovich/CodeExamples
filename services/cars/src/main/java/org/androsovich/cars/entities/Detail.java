package org.androsovich.cars.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.androsovich.cars.constatns.CommonConstants.*;

@Entity
@Table(name = "detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detail extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;

    @Override
    public String toString() {
        return LEFT_BRACKET + name + DELIMITER + serialNumber + RIGHT_BRACKET;
    }
}
