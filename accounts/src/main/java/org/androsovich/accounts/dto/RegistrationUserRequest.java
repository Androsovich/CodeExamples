package org.androsovich.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.androsovich.accounts.validation.Phone;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegistrationUserRequest {
    @NotBlank(message = "Please provide a first name")
    private String firstName;

    @NotBlank(message = "Please provide a last name")
    private String lastName;

    @NotNull
    @Past(message = "Please provide a birthday")
    private LocalDate birthday;

    @NotBlank(message = "Please provide a username")
    private String username;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    @NotBlank
    @Phone
    private String phone;

    @NotBlank(message = "email address must be not empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Min(0)
    private double balance;
}
