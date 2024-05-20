package org.androsovich.accounts.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.repositories.AccountRepository;
import org.androsovich.accounts.services.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public void save(Account account) {
    accountRepository.save(account);
    log.info("method - save : Account created for user - {} , balance - {} successfully.",
            account.getUser(), account.getBalance());
    }
}
