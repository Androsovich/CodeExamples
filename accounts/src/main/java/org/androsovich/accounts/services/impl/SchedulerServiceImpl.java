package org.androsovich.accounts.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.repositories.AccountRepository;
import org.androsovich.accounts.services.SchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.androsovich.accounts.constants.Constants.BALANCE_PERCENTAGE;
import static org.androsovich.accounts.constants.Constants.CRON;

@Service
@Slf4j
@AllArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {
    private AccountRepository accountRepository;

    @Scheduled(cron = CRON)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int increaseBalanceAccount() {
      int updatedRows = accountRepository.increaseBalance(BALANCE_PERCENTAGE);
      log.info("the number of accounts that have accrued interest: {}" , updatedRows);
      return updatedRows;
    }
}
