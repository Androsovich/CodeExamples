package org.androsovich.drivers;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.drivers.entities.Account;
import org.androsovich.drivers.entities.Currency;
import org.androsovich.drivers.entities.CurrencyExchangeRate;
import org.androsovich.drivers.entities.Driver;
import org.androsovich.drivers.repositories.AccountRepository;
import org.androsovich.drivers.repositories.CurrencyExchangeRateRepository;
import org.androsovich.drivers.repositories.CurrencyRepository;
import org.androsovich.drivers.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class DriversServiceApplication implements CommandLineRunner {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(DriversServiceApplication.class, args);
        log.info("Service B(Drivers Service) started successfully");
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        List<Driver> drivers = driverRepository.saveAll(
                List.of(
                        new Driver("first", "2344SDDA", "B", LocalDate.of(1982, 10, 20), 0),
                        new Driver("second", "27777DA", "C", LocalDate.of(1972, 07, 05), 10),
                        new Driver("third", "2344SD1111", "B", LocalDate.of(1983, 11, 21), 5),
                        new Driver("third1", "2344SD1111_1", "B", LocalDate.of(1983, 06, 24), 20),
                        new Driver("third2", "2344SD1111_2", "B", LocalDate.of(1987, 06, 24), 15),
                        new Driver("third3", "2344SD1111_3", "B", LocalDate.of(2000, 06, 25), 1),
                        new Driver("third4", "2344SD1111_4", "B", LocalDate.of(2001, 06, 25), 1)));

        List<Currency> currencies = currencyRepository.saveAll(
                List.of(
                        new Currency(1L, "red"),
                        new Currency(2L, "green"),
                        new Currency(3L, "blue")));
        //********************************************************************* red - 0, green-1, blue-2 *********************

        BigDecimal blueToRedRate = BigDecimal.ONE.divide(new BigDecimal("1.5"),5, RoundingMode.CEILING);
        BigDecimal blueToGreenRate = BigDecimal.ONE.divide(new BigDecimal("0.6"),5,RoundingMode.CEILING);

        CurrencyExchangeRate redToGreen = new CurrencyExchangeRate(currencies.get(0), currencies.get(1), new BigDecimal("2.5"));
        CurrencyExchangeRate redToBlue = new CurrencyExchangeRate(currencies.get(0), currencies.get(2), new BigDecimal("1.5"));
        CurrencyExchangeRate greenToBlue = new CurrencyExchangeRate(currencies.get(1), currencies.get(2), new BigDecimal("0.6"));
        CurrencyExchangeRate greenToRed = new CurrencyExchangeRate(currencies.get(1), currencies.get(0), new BigDecimal("0.4"));
        CurrencyExchangeRate blueToRed = new CurrencyExchangeRate(currencies.get(2), currencies.get(0), blueToRedRate);
        CurrencyExchangeRate blueToGreen = new CurrencyExchangeRate(currencies.get(2), currencies.get(1), blueToGreenRate);

        List<Account> accounts = List.of(
                new Account(drivers.get(0), currencies.get(2)),
                new Account(drivers.get(1), currencies.get(0)),
                new Account(drivers.get(2), currencies.get(1)),
                new Account(drivers.get(3), currencies.get(1)),
                new Account(drivers.get(4), currencies.get(0)),
                new Account(drivers.get(5), currencies.get(2)),
                new Account(drivers.get(6), currencies.get(0)));
        accountRepository.saveAll(accounts);
        currencyExchangeRateRepository.saveAll(List.of(redToGreen, redToBlue, greenToBlue, greenToRed, blueToRed, blueToGreen));
    }
}
