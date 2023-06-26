package org.androsovich.drivers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "CurrencyExchangeRate")
@Table(name = "currency_exchange_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeRate extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_currency_id")
    private Currency fromCurrency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_currency_id")
    private Currency toCurrency;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;
}
