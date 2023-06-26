package org.androsovich.drivers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    public Account(Driver driver, Currency currency){
        this.driver = driver;
        this.currency = currency;
    }

    @Id
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Driver driver;

    @ManyToOne()
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Min(0)
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
}
