package org.androsovich.accounts.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.androsovich.accounts.entities.Account;
import org.androsovich.accounts.entities.User;

import java.math.BigDecimal;

import static org.androsovich.accounts.constants.Constants.LIMIT_PERCENTAGE;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    Long id;
    @NotNull
    BigDecimal balance;

    public static Account toEntity(User user, double startBalance) {
        BigDecimal balance = BigDecimal.valueOf(startBalance);
        BigDecimal percentageLimit = new BigDecimal(startBalance * LIMIT_PERCENTAGE);
        return new Account(null, user, balance, percentageLimit);
    }
}
