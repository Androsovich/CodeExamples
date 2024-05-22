package org.androsovich.accounts.services;

import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.entities.Account;

public interface AccountService {
    void save(Account account);

    Account findById(Long id);

    void transferMoneyBetweenAccounts(AccountDto accountDto, Long recipientAccountId);
}
