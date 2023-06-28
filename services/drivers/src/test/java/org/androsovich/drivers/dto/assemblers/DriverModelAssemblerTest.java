package org.androsovich.drivers.dto.assemblers;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.drivers.dto.DriverResponse;
import org.androsovich.drivers.entities.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DriverModelAssemblerTest {

    private DriverModelAssembler driverModelAssembler;
    private List<Driver> drivers;

    @BeforeEach
    public void init() {
       log.info("init DriverModelAssemblerTest ");

       driverModelAssembler = new DriverModelAssembler();

       drivers =List.of(
               new Driver("test1", "passport_1", "BC", LocalDate.of(1990, 7, 12), 8),
               new Driver("test2", "passport_2", "ABC", LocalDate.of(1991, 9, 21), 11),
               new Driver("test3", "passport_3", "BC", LocalDate.of(1993, 7, 11), 5),
               new Driver("test4", "passport_4", "ABC", LocalDate.of(1992, 8, 8), 6),
               new Driver("test5", "passport_5", "BC", LocalDate.of(1990, 7, 1), 7)
       );
    }

    @Test
    void sizeContentInCollectionModelEqualTestSize() {
        assertEquals(driverModelAssembler.toCollectionModel(drivers).getContent().size(), drivers.size());
    }

    @Test
    void testMethodToModel() {
        Driver driver = new Driver("test5", "passport_5", "BC", LocalDate.of(1990, 7, 1), 7);
        driver.setId(1L);
        DriverResponse driverResponse =  driverModelAssembler.toModel(driver);
       assertEquals(driverResponse.getId(), driver.getId());
       assertEquals(driverResponse.getFullName(), driver.getFullName());
       assertEquals(driverResponse.getPassport(), driver.getPassport());
       assertEquals(driverResponse.getDriverLicense(), driver.getDriverLicense());
       assertEquals(driverResponse.getExperience(), driver.getExperience());

    }

    @Test
    void testMethodToModelMissingSomeFields(){
        Driver driver = new Driver();
        driver.setFullName("test5");
        driver.setExperience(8);
        driver.setId(1L);

        DriverResponse driverResponse =  driverModelAssembler.toModel(driver);
        assertEquals(driverResponse.getId(), driver.getId());
        assertEquals(driverResponse.getFullName(), driver.getFullName());
        assertEquals(driverResponse.getExperience(), driver.getExperience());
        assertNull(driverResponse.getDriverLicense());
        assertNull(driverResponse.getPassport());
    }
}