package org.androsovich.drivers.services.impl;

import org.androsovich.drivers.entities.Driver;
import org.androsovich.drivers.exceptions.DriverNotFoundException;
import org.androsovich.drivers.services.DriverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceImplTest {

    @Autowired
    DriverService driverService;

    @Test
    void findById() {
        Driver driver = driverService.findById(1L);
        assertNotNull(driver);
    }

    @Test
    void testFindByIdExceptedException() {
        assertThrows(DriverNotFoundException.class, () -> driverService.findById(10000L));
    }

    @Test
    void testSaveDriver() {
        Driver driverTest1 = new Driver("test1", "passport_1", "BC", LocalDate.of(1990, 7, 11), 11);
        Driver driver = driverService.save(driverTest1);
        assertNotNull(driver.getId());
        assertEquals(driver.getFullName(), driverTest1.getFullName());
        assertEquals(driver.getDriverLicense(), driverTest1.getDriverLicense());
        assertEquals(driver.getPassport(), driverTest1.getPassport());
        assertEquals(driver.getBirthday(), driverTest1.getBirthday());
        assertEquals(driver.getExperience(), driverTest1.getExperience());
    }

    @Test
    void getAll() {
        assertNotNull(driverService.getAll());
    }

    @Test
    @Transactional
    void testDeleteById() {
        Driver driverTest = new Driver("test5", "passport_5", "BC", LocalDate.of(1990, 7, 1), 7);
        Driver driver = driverService.save(driverTest);
        Long driveId = driver.getId();

        driverService.deleteById(driveId);
        assertThrows(DriverNotFoundException.class, () -> driverService.findById(driveId));
    }
}