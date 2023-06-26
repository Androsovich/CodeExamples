package org.androsovich.drivers.exceptions;

import static org.androsovich.drivers.constants.CommonConstants.WRONG_AMOUNT_MONEY;

public class MoneyException extends DriverServiceException {

    public MoneyException(String message) {
        super(WRONG_AMOUNT_MONEY + message);
    }
}
