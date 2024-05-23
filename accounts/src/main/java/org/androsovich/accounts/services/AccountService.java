package org.androsovich.accounts.services;

import org.androsovich.accounts.dto.account.TransferMoneyRequest;
import org.androsovich.accounts.entities.Account;

public interface AccountService {
    Account save(Account account);

    Account findById(Long id);

    void transferMoneyBetweenAccounts(TransferMoneyRequest transferMoneyRequest);
}
