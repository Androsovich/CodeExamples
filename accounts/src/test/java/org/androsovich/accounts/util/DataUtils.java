package org.androsovich.accounts.util;

import org.androsovich.accounts.dto.user.UserRequest;
import org.androsovich.accounts.entities.User;

import java.time.LocalDate;

public class DataUtils {

    public static User getJohnDoeTransient() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .middleName("Prank")
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
                .middleName("Prank")
                .userName("www")
                .password("werty")
                .birthday(LocalDate.of(2000, 10, 12))
                .email("john.doe@email.com")
                .phone("+7 911 243-45-76")
                .build();

        user.setId(1L);

        return user;
    }

    public static User getJohnDoePersistedWithDuplicateEmail() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .middleName("Prank")
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
                .middleName("Prank")
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
}
