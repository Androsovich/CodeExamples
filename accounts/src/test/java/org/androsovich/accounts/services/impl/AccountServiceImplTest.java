package org.androsovich.accounts.services.impl;

import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.dto.account.TransferMoneyRequest;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.exceptions.*;
import org.androsovich.accounts.repositories.AccountRepository;
import org.androsovich.accounts.util.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Test save account functionality")
    void givenAccountToSaveWhenSaveThenRepositoryIsCalled() {
        //given
        Account accountToSave = AccountDto.toEntity(DataUtils.getJohnDoePersisted(), 100.0);
        BDDMockito.given(accountRepository.findByUser(any(User.class)))
                .willReturn(null);

        BDDMockito.given(accountRepository.save(any(Account.class)))
                .willReturn(DataUtils.getAccountJohnDoePersisted());
        //when
        Account savedAccount = accountService.save(accountToSave);
        //then
        assertThat(savedAccount).isNotNull();
    }

    @Test
    @DisplayName("Test save account with duplicate user expected exception")
    void givenAccountToSaveWithDuplicateUserWhenSaveAccountThenExceptionThrow() {
        //given
        Account accountToSave = AccountDto.toEntity(DataUtils.getJohnDoePersisted(), 100.0);
        BDDMockito.given(accountRepository.findByUser(any(User.class)))
                .willReturn(DataUtils.getAccountJohnDoePersisted());
        //when
        assertThrows(AccountWithDuplicateUserException.class, ()-> accountService.save(accountToSave));
        //then
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Test transferMoneyBetweenAccounts successfully functionality")
    void givenTransferMoneyRequestWhenTransferMoneyThenBalanceSuccessfullyChanged() {
        //given
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountId(2)
                .toAccountId(1)
                .balance(BigDecimal.valueOf(20))
                .build();
        Account fromAccount = DataUtils.getAccountJohnDoePersisted();
        Account toAccount = DataUtils.getAccountMikeWongDtoPersisted();

        BDDMockito.given(accountRepository.findById(transferMoneyRequest.getFromAccountId()))
                .willReturn(Optional.of(fromAccount));
        BDDMockito.given(accountRepository.findById(transferMoneyRequest.getToAccountId()))
                .willReturn(Optional.of(toAccount));
        //when
        accountService.transferMoneyBetweenAccounts(transferMoneyRequest);
        //then
        assertEquals(BigDecimal.valueOf(80), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(70), toAccount.getBalance());
    }

    @Test
    @DisplayName("Test transferMoneyBetweenAccounts with equal accounts id functionality")
    void givenTransferMoneyRequestWithEqualsAccountIdWhenTransferMoneyThenExceptionThrow() {
        //given
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountId(1)
                .toAccountId(1)
                .balance(BigDecimal.valueOf(20))
                .build();
        //when
        assertThrows(AccountsEqualsForTransferException.class, ()-> accountService.transferMoneyBetweenAccounts(transferMoneyRequest));
        //then
        verify(accountRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Test transferMoneyBetweenAccounts with negative balance from request functionality")
    void givenTransferMoneyRequestWithNegativeBalanceWhenTransferMoneyThenExceptionThrow() {
        //given
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountId(2)
                .toAccountId(1)
                .balance(BigDecimal.valueOf(-20))
                .build();
        //when
        assertThrows(IllegalBalanceException.class, ()-> accountService.transferMoneyBetweenAccounts(transferMoneyRequest));
        //then
        verify(accountRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Test transferMoneyBetweenAccounts with invalid fromAccount id functionality")
    void givenTransferMoneyRequestWithInvalidFromAccountIdWhenTransferMoneyThenExceptionThrow() {
        //given
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountId(24)
                .toAccountId(1)
                .balance(BigDecimal.valueOf(45))
                .build();
       BDDMockito.given(accountRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        //when
        assertThrows(AccountNotFoundException.class, ()-> accountService.transferMoneyBetweenAccounts(transferMoneyRequest));
        //then
        verify(accountRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Test transferMoneyBetweenAccounts with invalid toAccount id functionality")
    void givenTransferMoneyRequestWithInvalidToAccountIdWhenTransferMoneyThenExceptionThrow() {
        //given
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountId(2)
                .toAccountId(100)
                .balance(BigDecimal.valueOf(20))
                .build();
        Account fromAccount = DataUtils.getAccountJohnDoePersisted();

        BDDMockito.given(accountRepository.findById(transferMoneyRequest.getFromAccountId()))
                .willReturn(Optional.of(fromAccount));
        BDDMockito.given(accountRepository.findById(transferMoneyRequest.getToAccountId()))
                .willReturn(Optional.empty());
        //when
        assertThrows(AccountNotFoundException.class, ()-> accountService.transferMoneyBetweenAccounts(transferMoneyRequest));
        //then
        verify(accountRepository, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("Test transferMoneyBetweenAccounts with balance higher then from account balance functionality")
    void givenTransferMoneyRequestWithBalanceHigherThenFromAccountBalanceWhenTransferMoneyThenExceptionThrow() {
        //given
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountId(2)
                .toAccountId(1)
                .balance(BigDecimal.valueOf(200))
                .build();
        Account fromAccount = DataUtils.getAccountJohnDoePersisted();
        Account toAccount = DataUtils.getAccountMikeWongDtoPersisted();

        BDDMockito.given(accountRepository.findById(transferMoneyRequest.getFromAccountId()))
                .willReturn(Optional.of(fromAccount));
        BDDMockito.given(accountRepository.findById(transferMoneyRequest.getToAccountId()))
                .willReturn(Optional.of(toAccount));
        //when
        assertThrows(NotEnoughMoneyException.class, ()-> accountService.transferMoneyBetweenAccounts(transferMoneyRequest));
        //then
        verify(accountRepository, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("Test findById successfully functionality")
    void givenValidIdWhenFindByIdThenNotExceptionThrow(){
        //given
        BDDMockito.given(accountRepository.findById(anyLong()))
                .willReturn(Optional.of(DataUtils.getAccountJohnDoePersisted()));
        //when
        accountService.findById(anyLong());
        //then
        verify(accountRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Test findById : invalid id functionality")
    void givenInValidIdWhenFindByIdThenExceptionThrow(){
        //given
        BDDMockito.given(accountRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        //when
        assertThrows(AccountNotFoundException.class, ()-> accountService.findById(anyLong()));
        //then
        verify(accountRepository, times(1)).findById(anyLong());
    }
}