package org.androsovich.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationUserRequest {
    @NotBlank(message = "Please provide a first name")
    private String firstName;
    @NotBlank(message = "Please provide a last name")
    private String lastName;
    @NotBlank(message = "Please provide a middle name")
    private String middleName;
    @NotBlank
    private String birthday;
    @NotBlank(message = "Please provide a username")
    private String username;
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;
    @NotBlank
    private String phone;
    @Email(message = "Please provide a valid email address")
    private String email;
    @Min(0)
    private int balance;

}
