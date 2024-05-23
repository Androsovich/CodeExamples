package org.androsovich.accounts.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.validation.Phone;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotNull
    private Long id;
    @Email
    private String email;
    @Phone
    private String phone;

    public User toEntity() {
       User user = new User();
       user.setId(id);
       user.setEmail(email);
       user.setPhone(phone);
       return user;
    }
}
