package org.androsovich.drivers.services;

import org.androsovich.drivers.dto.Money;
import org.androsovich.drivers.entities.Account;
import org.springframework.transaction.annotation.Transactional;

public interface AccountService {
    Account findAccountById(Long id);

    void withdraw(Money money, Long id);

    void deposit(Money money, Long id);

    @Transactional(readOnly = true)
    Money getBalanceByAccountID(Long id);

    Money getBalanceByAccountID(String currency, Long id);

}
