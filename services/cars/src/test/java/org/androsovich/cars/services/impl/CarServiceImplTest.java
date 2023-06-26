package org.androsovich.cars.services.impl;

import org.androsovich.cars.entities.Car;
import org.androsovich.cars.entities.Detail;
import org.androsovich.cars.exceptions.CarNotFoundException;
import org.androsovich.cars.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarServiceImplTest {

    @Autowired
    CarService carService;

    @Test
    void testGetAll() {
        Page<Car> cars = carService.getAll();
        assertNotNull(cars.getContent());
        assertThat(cars.getContent()).isNotEmpty();
    }

    @Test
    void testSaveMethod() {
        Car car = carService.save(new Car("vin", "stateNumber"));

        assertNotNull(car);
        assertNotNull(car.getId());

        assertThat(car.getVin()).isEqualTo("vin");
        assertThat(car.getStateNumber()).isEqualTo("stateNumber");
    }

    @Test
    void testDeleteByIdMethod() {
        carService.deleteById(2L);
        assertThatThrownBy(() -> carService.findById(2L)).isInstanceOf(CarNotFoundException.class);
    }

    @Test
    @Transactional
    void testAddDetailMethod() {
        Car car = carService.save(new Car("test1", "state12"));
        carService.addDetail(new Detail("detail_1", "number_1", new Car()), car.getId());
        carService.addDetail(new Detail("detail_2", "number_2", new Car()), car.getId());

        List<Detail> details = carService.findById(car.getId()).getDetails();
        assertThat(details.size()).isNotZero();
        assertThat(details.get(0).getName()).isEqualTo("detail_1");
        assertThat(details.get(1).getSerialNumber()).isEqualTo("number_2");
        assertThat(details.get(1).getCar().getId()).isEqualTo(car.getId());
    }

    @Test
    void testFindByIdMethod() {
        Car carTest_1 = carService.save(new Car("test1", "state1"));
        Car carTest_2 = carService.save(new Car("test2", "state2"));

        assertThat(carTest_1.getId()).isEqualTo(carService.findById(carTest_1.getId()).getId());
        assertThat(carTest_2.getId()).isEqualTo(carService.findById(carTest_2.getId()).getId());
    }

    @Test
    void testFindByIdExpectedException() {
        assertThatThrownBy(() -> carService.findById(20000L)).isInstanceOf(CarNotFoundException.class);
    }

    @Test
    void testFindByIdWithFalseLazyFetch() {
        Car car = carService.save(new Car("test1", "state12"));
        carService.addDetail(new Detail("detail_1", "number_1", new Car()), car.getId());
        carService.addDetail(new Detail("detail_2", "number_2", new Car()), car.getId());

        Car expected = carService.findById(car.getId(), false);

        assertThat(expected.getDetails().size()).isPositive();
    }

    @Test
    void testAddDriverMethod() {
        Car car = carService.save(new Car("vin_7", "state_11"));
        carService.addDriver(34L, car.getId());

        assertThat(carService.findById(car.getId()).getDriverId()).isEqualTo(34L);
    }
    @Test
    void testAddDriverMethodExpectedCarNotFoundException() {
        assertThatThrownBy(() -> carService.addDriver(34L, 40000L)).isInstanceOf(CarNotFoundException.class);
    }
}