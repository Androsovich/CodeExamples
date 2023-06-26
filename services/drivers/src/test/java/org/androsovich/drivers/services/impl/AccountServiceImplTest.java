package org.androsovich.drivers.services.impl;

import org.androsovich.drivers.dto.Money;
import org.androsovich.drivers.exceptions.AccountNotFoundException;
import org.androsovich.drivers.exceptions.CurrencyNotFoundException;
import org.androsovich.drivers.exceptions.MoneyException;
import org.androsovich.drivers.exceptions.NotEnoughMoneyException;
import org.androsovich.drivers.repositories.AccountRepository;
import org.androsovich.drivers.repositories.CurrencyExchangeRateRepository;
import org.androsovich.drivers.repositories.CurrencyRepository;
import org.androsovich.drivers.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private AccountService accountService;

    @Test
    void findAccountByIdThrowExceptionWhenIdNotFound() {
        assertThrows(AccountNotFoundException.class, () -> accountService.findAccountById(100000L));
    }

    @Test
    void testGetBalanceIncorrectCurrencyExpectedException() {
        assertThrows(CurrencyNotFoundException.class, () -> accountService.getBalanceByAccountID("money", 1L));
    }

    @Test
    void testGetBalanceAccountNotFoundException() {
        assertThrows(AccountNotFoundException.class, () -> accountService.getBalanceByAccountID("money", 154353453L));
    }

    @Test
    void testGetBalanceInAnotherCurrency() {
        Double expectedAmount = 1000d;
        accountService.deposit(new Money("green", "2500"), 4L);
        Money money = accountService.getBalanceByAccountID( "red", 4L);
        assertEquals(expectedAmount, Double.valueOf(money.getAmount()));
    }

    @Test
    void testGetBalanceInSameCurrency() {
        Double expectedAmount = 2500d;
        accountService.deposit(new Money("green", "2500"), 3L);
        Money money = accountService.getBalanceByAccountID( 3L);
        assertEquals(expectedAmount, Double.valueOf(money.getAmount()));
    }

    @Test
    void withdrawWhenBalanceIsZeroExpectedException() {
        Money money = new Money("red", "500");
        assertThrows(NotEnoughMoneyException.class, () -> accountService.withdraw(money, 1L));
    }

    @Test
    void testWithdrawWhenCurrencyNotFound() {
        Money money = new Money("ffdee", "55");
        assertThrows(CurrencyNotFoundException.class, () -> accountService.withdraw(money, 1L));
    }

    @Test
    void testWithdrawWhenWrongAmountMoney() {
        Money money = new Money("red", "-55");
        assertThrows(MoneyException.class, () -> accountService.withdraw(money, 1L));
    }

    @Test
    void testDepositWhenWrongAmountMoney() {
        Money money = new Money("red", "-51033");
        assertThrows(MoneyException.class, () -> accountService.deposit(money, 1L));
    }

    @Test
    void testDepositWhenCurrencyNotFound() {
        Money money = new Money("ffdee", "55");
        assertThrows(CurrencyNotFoundException.class, () -> accountService.deposit(money, 1L));
    }

    @Test
    void testWithdrawAndDepositInSameCurrency() {
        final Double expectedAmount = 223d;
        String currency = accountService.findAccountById(1L).getCurrency().getName();
        accountService.deposit(new Money(currency, "1000"), 1L);
        accountService.withdraw(new Money(currency, "777"), 1L);
        Money money = accountService.getBalanceByAccountID(1L);

        assertEquals(expectedAmount, Double.valueOf(money.getAmount()));
    }

    @Test
    void testWithdrawAndDepositInDifferentCurrencies() {
        final Double expectedAmount = 800d;
        String currency = accountService.findAccountById(2L).getCurrency().getName();
        accountService.deposit(new Money(currency, "1000"), 2L);
        accountService.withdraw(new Money("green", "500"), 2L);
        Money money = accountService.getBalanceByAccountID(2L);

        assertEquals(expectedAmount, Double.valueOf(money.getAmount()));
    }
}