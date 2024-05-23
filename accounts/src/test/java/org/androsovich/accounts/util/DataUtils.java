package org.androsovich.accounts.util;

import org.androsovich.accounts.dto.account.AccountDto;
import org.androsovich.accounts.dto.user.UserRequest;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.androsovich.accounts.constants.Constants.LIMIT_PERCENTAGE;

public class DataUtils {

    public static User getJohnDoeTransient() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("www")
                .password("werty")
                .birthday(LocalDate.of(2000, 10, 12))
                .email("john.doe@email.com")
                .phone("+7 911 243-45-76")
                .build();
    }

    public static User getJohnDoePersisted() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("www")
                .password("werty")
                .birthday(LocalDate.of(2000, 10, 12))
                .email("john.doe@email.com")
                .phone("+7 911 243-45-76")
                .build();

        user.setId(1L);

        return user;
    }

    public static User getMikeWongPersisted() {
        User user = User.builder()
                .firstName("Mike")
                .lastName("Wong")
                .userName("www1")
                .password("werty1")
                .birthday(LocalDate.of(1992, 9, 20))
                .email("mike.wong@email.com")
                .phone("+7 911 230-45-76")
                .build();

        user.setId(2L);

        return user;
    }

    public static User getMikeWongTransient() {
        return User.builder()
                .firstName("Mike")
                .lastName("Wong")
                .userName("www1")
                .password("werty1")
                .birthday(LocalDate.of(1992, 9, 20))
                .email("mike.wong@email.com")
                .phone("+7 911 230-45-76")
                .build();
    }

    public static User getJohnDoePersistedWithDuplicateEmail() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("www")
                .password("werty")
                .birthday(LocalDate.of(2000, 10, 12))
                .email("john.doe@email.com")
                .phone("+7 911 243-44-75")
                .build();

        user.setId(1L);

        return user;
    }

    public static User getJohnDoePersistedWithDuplicatePhone() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("www")
                .password("werty")
                .birthday(LocalDate.of(2000, 10, 12))
                .email("john.doe54@email.com")
                .phone("+7 911 243-45-76")
                .build();

        user.setId(1L);

        return user;
    }

    public static UserRequest getJohnDoeDtoTransient() {
        return UserRequest.builder()
                .id(1L)
                .phone("+7 911 243-45-76")
                .email("john.doe@email.com")
                .build();
    }

    public static Account getAccountJohnDoePersisted() {
        return Account.builder()
                .id(1L)
                .user(DataUtils.getJohnDoePersisted())
                .balance(new BigDecimal(100))
                .percentageLimit(new BigDecimal(100 * LIMIT_PERCENTAGE))
                .build();
    }

    public static Account getAccountMikeWongDtoPersisted() {
        return Account.builder()
                .id(1L)
                .user(DataUtils.getMikeWongPersisted())
                .balance(new BigDecimal(50))
                .percentageLimit(new BigDecimal(50 * LIMIT_PERCENTAGE))
                .build();
    }

    public static AccountDto getAccountDtoJoeDoe() {
        return AccountDto.builder()
                .id(1L)
                .balance(new BigDecimal(100))
                .build();
    }
    public static AccountDto getAccountDtoTransfer() {
        return AccountDto.builder()
                .id(1L)
                .balance(new BigDecimal(50))
                .build();
    }
}
