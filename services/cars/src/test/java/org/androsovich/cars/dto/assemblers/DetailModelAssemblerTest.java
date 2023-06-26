package org.androsovich.cars.dto.assemblers;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.cars.dto.DetailResponse;
import org.androsovich.cars.entities.Car;
import org.androsovich.cars.entities.Detail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class DetailModelAssemblerTest {

    private DetailModelAssembler detailModelAssembler;
    private List<Detail> details;

    @BeforeEach
    public void init() {
        log.info("init DetailModelAssemblerTest ");

        detailModelAssembler = new DetailModelAssembler();

        details =List.of(
                new Detail("test1","233243dFFD", new Car("2133", "stateNumber_1")),
                new Detail("test2","233243dFFD111", null),
                new Detail("test3","233243dFFD222", new Car("DA12", "stateNumber_2")),
                new Detail("test4","233243dFFD333", null)
        );
    }

    @Test
    void sizeContentInCollectionModelEqualTestSize() {
        assertThat(detailModelAssembler.toCollectionModel(details).getContent().size()).isEqualTo(4);
    }

    @Test
    void testMethodToModel() {
        Car car = new Car("DA12", "stateNumber_2");
        car.setId(7L);
        Detail detail = new Detail("test3","233243dFFD222", car);
        detail.setId(1L);
        DetailResponse detailResponse = detailModelAssembler.toModel(detail);
        assertThat(detailResponse.getId()).isEqualTo(detail.getId());
        assertThat(detailResponse.getName()).isEqualTo(detail.getName());
        assertThat(detailResponse.getSerialNumber()).isEqualTo(detail.getSerialNumber());
        assertThat(detailResponse.getCarId()).isEqualTo(detail.getCar().getId());
    }
}