package org.androsovich.accounts.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.dto.account.AccountDto;
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
    Account obtainedAccount = accountRepository.save(account);
    log.info("method - save : Account created for user - {} , account - {} successfully.",
            account.getUser(), obtainedAccount);
    }

    @Override
    public Account findById(Long id) {
        return null;
    }

    @Override
    public void transferMoneyBetweenAccounts(AccountDto accountDto, Long recipientAccountId) {

    }
}
