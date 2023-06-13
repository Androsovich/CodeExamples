package com.epam.summer.lab.utils;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.utils.validators.StatusValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class StatusValidatorImplTest {

    @Autowired
    StatusValidator statusValidator;

    @Test
    void checkStatusSuccess() {
        final String status = "ACTIVE";
        assertTrue(statusValidator.checkStatus(status));
    }

    @Test
    void checkArrayStatuses() {
        String[] testArray = {"ACTIVE", "INACTIVE", "COMPLETED"};
        assertArrayEquals(testArray, statusValidator.getStatuses());
    }
}