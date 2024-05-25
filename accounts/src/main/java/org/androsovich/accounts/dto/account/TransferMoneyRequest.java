package org.androsovich.accounts.dto.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyRequest {
    long fromAccountId;

    long toAccountId;

    @NotNull
    @Min(0)
    BigDecimal balance;
}
