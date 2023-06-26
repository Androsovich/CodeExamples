package org.androsovich.drivers.repositories;

import org.androsovich.drivers.entities.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {
    @Query("select ex.exchangeRate from CurrencyExchangeRate ex" +
                    " where ex.toCurrency.id = :toCurrencyId " +
                    " and ex.fromCurrency.id = (select curr.id from Currency curr where curr.name = :fromCurrencyName)")
    BigDecimal getExchangeRate(@Param("toCurrencyId") Long toCurrencyId, @Param("fromCurrencyName") String fromCurrencyName);
}
