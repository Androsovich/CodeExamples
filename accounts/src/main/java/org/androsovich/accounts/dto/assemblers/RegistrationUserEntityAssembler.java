package org.androsovich.accounts.dto.assemblers;

import lombok.AllArgsConstructor;
import org.androsovich.accounts.dto.RegistrationUserRequest;
import org.androsovich.accounts.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class RegistrationUserEntityAssembler  implements EntityAssembler<RegistrationUserRequest, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User toEntity(RegistrationUserRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .birthday(LocalDate.parse(request.getBirthday()))
                .userName(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }
}

