package org.androsovich.cars.dto.assemblers;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.cars.dto.CarResponse;
import org.androsovich.cars.entities.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CarModelAssemblerTest {

    private CarModelAssembler carModelAssembler;
    private List<Car> cars;

    @BeforeEach
    public void init() {
        log.info("init CarModelAssemblerTest ");

        carModelAssembler = new CarModelAssembler();

        cars = List.of(
                new Car("test1", "stateNumber_1"),
                new Car("test2", "stateNumber_2"),
                new Car("test3", "stateNumber_3"),
                new Car("test4", "stateNumber_4"),
                new Car("test5", "stateNumber_5")
        );
    }

    @Test
    void sizeContentInCollectionModelEqualTestSize() {
        assertThat(carModelAssembler.toCollectionModel(cars).getContent().size()).isEqualTo(5);
    }

    @Test
    void testMethodToModel() {
        Car car = new Car("test7", "state_7");
        car.setId(1L);
        CarResponse carResponse = carModelAssembler.toModel(car);
        assertEquals(carResponse.getId(), car.getId());
        assertEquals(carResponse.getVin(), car.getVin());
        assertEquals(carResponse.getStateNumber(), car.getStateNumber());
    }
}