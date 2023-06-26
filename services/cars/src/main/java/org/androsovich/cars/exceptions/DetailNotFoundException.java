package org.androsovich.cars.exceptions;

import static org.androsovich.cars.constatns.CommonConstants.DETAIL_NOT_FOUND_ID;

public class DetailNotFoundException extends RuntimeException {

    public DetailNotFoundException(Long id) {
        super(DETAIL_NOT_FOUND_ID + id);
    }
}
