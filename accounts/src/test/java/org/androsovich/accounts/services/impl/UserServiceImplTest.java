package org.androsovich.accounts.services.impl;

import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.repositories.UserRepository;
import org.androsovich.accounts.util.DataUtils;
import org.androsovich.exceptions.UserWithDuplicateEmailException;
import org.androsovich.exceptions.UserWithDuplicatePhoneException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Test save user functionality")
    void givenUserToSaveWhenSaveThenRepositoryIsCalled(){
        //given
        User userToSave = DataUtils.getJohnDoeTransient();
        BDDMockito.given(userRepository.findByEmailOrPhone(anyString(), anyString()))
                .willReturn(null);

        BDDMockito.given(userRepository.save(any(User.class)))
                .willReturn(DataUtils.getJohnDoePersisted());
        //when
        User savedDeveloper = userService.save(userToSave);
        //then
        assertThat(savedDeveloper).isNotNull();
    }

    @Test
    @DisplayName("Test save developer with duplicate email expected exception")
    void givenUserToSaveWithDuplicateEmailWhenSaveUserThenExceptionThrow() {
        //given
        User userToSave = DataUtils.getJohnDoeTransient();
        BDDMockito.given(userRepository.findByEmailOrPhone(anyString(), anyString()))
                .willReturn(DataUtils.getJohnDoePersistedWithDuplicateEmail());
        //when
        assertThrows(UserWithDuplicateEmailException.class, ()-> userService.save(userToSave));
        //then
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Test save developer with duplicate phone expected exception")
    void givenUserToSaveWithDuplicatePhoneWhenSaveUserThenExceptionThrow() {
        //given
        User userToSave = DataUtils.getJohnDoeTransient();
        BDDMockito.given(userRepository.findByEmailOrPhone(anyString(), anyString()))
                .willReturn(DataUtils.getJohnDoePersistedWithDuplicatePhone());
        //when
        assertThrows(UserWithDuplicatePhoneException.class, ()-> userService.save(userToSave));
        //then
        verify(userRepository, never()).save(any(User.class));
    }
}