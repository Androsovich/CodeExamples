package org.androsovich.applications.utils;

import org.androsovich.applications.entities.embeddeds.Phone;
import org.androsovich.applications.exceptions.BadFeignResponseException;
import org.androsovich.applications.exceptions.PhoneException;

import java.util.Arrays;

import static org.androsovich.applications.constants.Constants.*;

public class PhoneUtils {

    private PhoneUtils() {}

    public static Phone processingResponse(Phone[] responsePhone) {
        if (responsePhone.length > 1) {
            throw new BadFeignResponseException(BAD_FEIGN_RESPONSE + responsePhone.length);
        }
        return Arrays.stream(responsePhone)
                .findAny()
                .filter(phone->MOBILE_PHONE_TYPE.equals(phone.getType()))
                .orElseThrow(() -> new PhoneException(PHONE_MUST_BE_MOBILE));
    }
}
