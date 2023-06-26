package org.androsovich.drivers.exceptions;

import static org.androsovich.drivers.constants.CommonConstants.CURRENCY_NOT_FOUND_NAME;

public class CurrencyNotFoundException extends DriverServiceException {

    public CurrencyNotFoundException(String name) {
        super(CURRENCY_NOT_FOUND_NAME + name);
    }
}