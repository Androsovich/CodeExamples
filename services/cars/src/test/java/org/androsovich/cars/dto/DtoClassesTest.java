package org.androsovich.cars.dto;

import org.androsovich.cars.entities.Car;
import org.androsovich.cars.entities.Detail;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DtoClassesTest {

    private final ModelMapper mapper = new ModelMapper();

    @Test
    void testDTOCarRequestToCar(){
        CarRequest carRequest = new CarRequest("wewe", "2111221", "reno", "reno", LocalDate.of(2000, 07, 21));
        Car car = mapper.map(carRequest, Car.class);

        assertThat(car.getVin()).isEqualTo(carRequest.getVin());
        assertThat(car.getStateNumber()).isEqualTo(carRequest.getStateNumber());
        assertThat(car.getBrand()).isEqualTo(carRequest.getBrand());
        assertThat(car.getManufacturer()).isEqualTo(carRequest.getManufacturer());
        assertThat(car.getYearOfManufacture()).isEqualTo(carRequest.getYearOfManufacture());
    }

    @Test
    void testDTODetailRequestToDetail(){
      DetailRequest detailRequest = new DetailRequest("test1", "number_test1");
      Detail detail = mapper.map(detailRequest, Detail.class);

      assertThat(detail.getName()).isEqualTo(detailRequest.getName());
      assertThat(detail.getSerialNumber()).isEqualTo(detailRequest.getSerialNumber());
    }
}
