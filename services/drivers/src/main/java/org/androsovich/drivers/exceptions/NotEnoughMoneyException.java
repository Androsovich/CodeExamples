package org.androsovich.drivers.exceptions;

import static org.androsovich.drivers.constants.CommonConstants.NOT_ENOUGH_MONEY_ON_ACCOUNT;

public class NotEnoughMoneyException extends DriverServiceException{

    public NotEnoughMoneyException(final Long id) {
        super(NOT_ENOUGH_MONEY_ON_ACCOUNT + id);
    }
}