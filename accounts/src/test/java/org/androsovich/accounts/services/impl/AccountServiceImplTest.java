package org.androsovich.accounts.services.impl;

import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.repositories.AccountRepository;
import org.androsovich.accounts.util.DataUtils;
import org.androsovich.exceptions.AccountWithDuplicateUserException;
import org.androsovich.exceptions.UserWithDuplicateEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    @DisplayName("Test save account with duplicate email expected exception")
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
}