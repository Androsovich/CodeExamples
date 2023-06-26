package org.androsovich.drivers.utils.validators;

import org.androsovich.drivers.dto.Money;
import org.androsovich.drivers.exceptions.MoneyException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.*;

@Component
public class MoneyValidator implements Validator<Money> {

    public void validate(Money money) {
        String amount = money.getAmount();
        if (!(isNumeric(amount) && (Math.signum(Double.parseDouble(amount)) >= 0))) {
            throw new MoneyException(amount);
        }
    }
}

