package org.androsovich.cars.exceptions;

import static org.androsovich.cars.constatns.CommonConstants.CAR_NOT_FOUND_ID;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Long id) {
        super(CAR_NOT_FOUND_ID + id);
    }
}
