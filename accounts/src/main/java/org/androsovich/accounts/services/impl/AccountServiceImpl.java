package org.androsovich.accounts.services.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.accounts.dto.account.TransferMoneyRequest;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.exceptions.*;
import org.androsovich.accounts.repositories.AccountRepository;
import org.androsovich.accounts.services.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

import static org.androsovich.accounts.constants.Constants.*;


@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Account save(Account account) {
    Account duplicatedAccount = accountRepository.findByUser(account.getUser());

    if(Objects.nonNull(duplicatedAccount)) {
        log.error(ACCOUNT_EXISTS_MESSAGE + "{}", account.getUser());
        throw new AccountWithDuplicateUserException(ACCOUNT_EXISTS_MESSAGE + account.getUser());
    }

    Account obtainedAccount = accountRepository.save(account);
    log.info("method - save : Account created for user - {} , account - {} successfully.",
            account.getUser(), obtainedAccount);
    return obtainedAccount;
    }

    @Override
    @Transactional
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(()-> new AccountNotFoundException(ACCOUNT_NOT_FOUND_BY_ID + id));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transferMoneyBetweenAccounts(@Valid @NotNull TransferMoneyRequest transferMoneyRequest) {

        validateTransferMoneyRequest(transferMoneyRequest);

        Account fromAccount = accountRepository.findById(transferMoneyRequest.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_BY_ID + transferMoneyRequest.getFromAccountId()));

        Account toAccount = accountRepository.findById(transferMoneyRequest.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_BY_ID + transferMoneyRequest.getToAccountId()));

        BigDecimal withdrawBalance = fromAccount.getBalance().subtract(transferMoneyRequest.getBalance());

        if (withdrawBalance.signum() < 0) {
            log.error(ACCOUNT_NOT_FOUND_ENOUGH_MONEY + "  {} : withdraw  {}", fromAccount.getBalance(), transferMoneyRequest.getBalance());
            throw new NotEnoughMoneyException(ACCOUNT_NOT_FOUND_ENOUGH_MONEY +
                    fromAccount.getBalance() + " : withdraw  " + transferMoneyRequest.getBalance());
        }

        fromAccount.setBalance(withdrawBalance);

        log.info("Withdraw money  {} from account balance  {} successfully. Account balance - {}. Account id - {}",
                transferMoneyRequest.getBalance(), fromAccount.getBalance(), withdrawBalance, fromAccount.getId());

        BigDecimal deposit = transferMoneyRequest.getBalance().add(toAccount.getBalance());
        toAccount.setBalance(deposit);
        log.info("Deposit money {} from account balance  {} successfully. Account balance {}. Account id - {}",
                transferMoneyRequest.getBalance(), toAccount.getBalance(), deposit, toAccount.getId());
    }

    private void validateTransferMoneyRequest(TransferMoneyRequest transferMoneyRequest) {
        if (transferMoneyRequest.getToAccountId() == transferMoneyRequest.getFromAccountId()) {

            log.error(ACCOUNTS_EQUALS_FOR_TRANSFER + " fromAccountId =  {}, toAccountId = {} ",
                    transferMoneyRequest.getFromAccountId(), transferMoneyRequest.getToAccountId());

            throw new AccountsEqualsForTransferException(ACCOUNTS_EQUALS_FOR_TRANSFER);
        } else if (transferMoneyRequest.getBalance().signum() < 0) {

            log.error(BALANCE_MUST_BE_NOT_NEGATIVE + " {} ",transferMoneyRequest.getBalance());

            throw new IllegalBalanceException(BALANCE_MUST_BE_NOT_NEGATIVE);
        }
    }
}
