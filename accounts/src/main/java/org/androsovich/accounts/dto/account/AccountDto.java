package org.androsovich.accounts.dto.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

import static org.androsovich.accounts.constants.Constants.LIMIT_PERCENTAGE;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends RepresentationModel<AccountDto> {
    @NotNull
    long id;

    @NotNull
    @Min(0)
    BigDecimal balance;

    public static Account toEntity(User user, double startBalance) {
        BigDecimal balance = BigDecimal.valueOf(startBalance);
        BigDecimal percentageLimit = BigDecimal.valueOf(startBalance * LIMIT_PERCENTAGE);
        return new Account(null, user, balance, percentageLimit);
    }
}
