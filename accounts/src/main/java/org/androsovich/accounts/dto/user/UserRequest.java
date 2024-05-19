package org.androsovich.accounts.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.androsovich.accounts.entities.User;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotNull
    private Long id;
    private String email;
    private String phone;

    public User toEntity() {
       User user = new User();
       user.setId(id);
       user.setEmail(email);
       user.setPhone(phone);
       return user;
    }
}
