package org.androsovich.drivers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Currency")
@Table(name = "currency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends BaseEntity {

    public Currency(Long id, String name) {
        setId(id);
        this.name = name;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "fromCurrency", cascade = CascadeType.ALL)
    private List<CurrencyExchangeRate> fromCurrenciesList = new ArrayList<>();

    @OneToMany(mappedBy = "toCurrency", cascade = CascadeType.ALL)
    private List<CurrencyExchangeRate> toCurrenciesList = new ArrayList<>();

}
