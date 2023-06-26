package org.androsovich.drivers.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver extends BaseEntity {

    @NotNull
    @Column(name = "full_name")
    @Size(min = 1, max = 25)
    private String fullName;

    @Column(name = "passport")
    @Size(min = 1, max = 25)
    private String passport;

    @Column(name = "driver_license")
    @Size(min = 1, max = 3)
    private String driverLicense;

    @Column(name = "birthday")
    @NotNull
    @Past
    private LocalDate birthday;

    @Column(name = "experience")
    @Max(45)
    private int experience;
}
