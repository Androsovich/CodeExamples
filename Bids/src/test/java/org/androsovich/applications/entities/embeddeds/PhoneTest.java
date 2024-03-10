package org.androsovich.applications.entities.embeddeds;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.applications.exceptions.BadFeignResponseException;
import org.androsovich.applications.exceptions.PhoneException;
import org.androsovich.applications.utils.PhoneUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class PhoneTest {
    private Gson gsonDecoder;
    private String json;

    @BeforeEach
    public void init() {
        log.info("init PhoneTest");

        gsonDecoder = new Gson();
        json = "{\"source\":\"+7911243-45-68\"," +
                "\"type\":\"Мобильный\",\"phone\":\"+7911243-45-68\"," +
                "\"country_code\":\"7\",\"city_code\":\"911\"," +
                "\"number\":\"2434568\",\"extension\":null," +
                "\"provider\":\"ПАО\\\"МобильныеТелеСистемы\\\"\"," +
                "\"country\":\"Россия\",\"region\":\"Санкт-ПетербургиЛенинградскаяобласть\"," +
                "\"city\":null,\"timezone\":\"UTC+3\",\"qc_conflict\":0,\"qc\":0}";
    }

    @Test
    void testConvertJSONtoPhoneObject() {
        Phone phone = gsonDecoder.fromJson(json, Phone.class);

        assertNotNull(phone.getPhone());
        assertNotNull(phone.getType());
        assertNotNull(phone.getCityCode());
        assertNotNull(phone.getCountryCode());
    }

    @Test
    void testResponseContainsMorePhonesExpectedException() {
        Phone[] response = {gsonDecoder.fromJson(json, Phone.class), gsonDecoder.fromJson(json, Phone.class)};
        assertThrows(BadFeignResponseException.class, () -> PhoneUtils.processingResponse(response));
    }

    @Test
    void testPhoneContainsInvalidTypeExpectedException() {
        Phone phone = gsonDecoder.fromJson(json, Phone.class);
        phone.setType("test");
        assertThrows(PhoneException.class, () -> PhoneUtils.processingResponse(new Phone[]{phone}));
    }
}