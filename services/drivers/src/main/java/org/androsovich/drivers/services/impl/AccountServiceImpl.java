package org.androsovich.drivers.services.impl;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.drivers.dto.Money;
import org.androsovich.drivers.entities.Account;
import org.androsovich.drivers.entities.Currency;
import org.androsovich.drivers.exceptions.AccountNotFoundException;
import org.androsovich.drivers.exceptions.CurrencyNotFoundException;
import org.androsovich.drivers.exceptions.NotEnoughMoneyException;
import org.androsovich.drivers.repositories.AccountRepository;
import org.androsovich.drivers.repositories.CurrencyExchangeRateRepository;
import org.androsovich.drivers.repositories.CurrencyRepository;
import org.androsovich.drivers.services.AccountService;
import org.androsovich.drivers.utils.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.function.BinaryOperator;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CurrencyExchangeRateRepository exchangeRateRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    Validator moneyValidator;

    @Override
    @Transactional
    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void withdraw(@NotNull Money money, @NotNull Long id) {
        BigDecimal resultBalance = accountBalanceOperation(money, id, BigDecimal::subtract);
        if (resultBalance.signum() < 0) {
            throw new NotEnoughMoneyException(id);
        }
        log.info("Withdraw money - {} in currency - {} successfully. Account balance - {}.",
                money.getAmount(), money.getCurrency(), resultBalance);
        accountRepository.updateAccountBalance(resultBalance, id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deposit(@NotNull Money money, @NotNull Long id) {
        BigDecimal resultBalance = accountBalanceOperation(money, id, BigDecimal::add);
        log.info("deposit - {} in currency - {} successfully. Account balance - {}.",
                money.getAmount(), money.getCurrency(), resultBalance);

        accountRepository.updateAccountBalance(resultBalance, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Money getBalanceByAccountID(Long id) {
        log.info("get balance by account id = {}", id);
        return getBalanceByAccountID(null, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Money getBalanceByAccountID(String currency, Long id) {
        Account account = findAccountById(id);
        String currencyName = Objects.nonNull(currency) ? currency : account.getCurrency().getName();

        log.info("get balance by account id = {} and show in currency - {}", id, currency);

        Currency toCurrency = currencyRepository.findByName(currencyName).orElseThrow(()-> new CurrencyNotFoundException(currencyName));
        BigDecimal exchangeRate = getCurrencyExchangeRate(account.getCurrency(), toCurrency);
        return new Money(currencyName, account.getBalance().multiply(exchangeRate).toString());
    }

    private BigDecimal accountBalanceOperation(Money money, Long id, BinaryOperator<BigDecimal> operation) {
        moneyValidator.validate(money);

        Account account = findAccountById(id);
        Currency currency = account.getCurrency();
        Currency fromCurrency = currencyRepository.findByName(money.getCurrency()).orElseThrow(()-> new CurrencyNotFoundException(money.getCurrency()));

        BigDecimal exchangeRate = getCurrencyExchangeRate(fromCurrency, currency);
        BigDecimal moneyWithExchangeRate = new BigDecimal(money.getAmount(), MathContext.DECIMAL32).multiply(exchangeRate);

        return operation.apply(account.getBalance(), moneyWithExchangeRate);
    }

    private BigDecimal getCurrencyExchangeRate(@NotNull Currency fromCurrency, @NotNull Currency currency) {
        BigDecimal exchangeRate = BigDecimal.ONE;
        String toCurrency = currency.getName();

        if (!fromCurrency.getName().equals(toCurrency)) {
            exchangeRate = exchangeRateRepository.getExchangeRate(currency.getId(), fromCurrency.getName());
            log.info("currencies are not equal : fromCurrency = {}, fromCurrency = {}", fromCurrency, fromCurrency);
        }
        log.info("exchangeRate = {}", exchangeRate.toString());
        return exchangeRate;
    }
}
