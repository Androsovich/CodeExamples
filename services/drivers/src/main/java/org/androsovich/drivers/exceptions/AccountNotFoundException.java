package org.androsovich.drivers.exceptions;

import static org.androsovich.drivers.constants.CommonConstants.ACCOUNT_NOT_FOUND_ID;

public class AccountNotFoundException extends DriverServiceException {
    public AccountNotFoundException(Long id) {
        super(ACCOUNT_NOT_FOUND_ID + id);
    }
}
