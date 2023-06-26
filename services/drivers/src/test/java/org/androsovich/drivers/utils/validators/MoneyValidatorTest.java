package org.androsovich.drivers.utils.validators;

import org.androsovich.drivers.dto.Money;
import org.androsovich.drivers.exceptions.MoneyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyValidatorTest {

    @Test
    void validateWrongAMountMoneyExpectedMoneyException() {
        Money money = new Money("dfdfd", "-332");
        assertThrows(MoneyException.class, ()-> new MoneyValidator().validate(money));
    }
}