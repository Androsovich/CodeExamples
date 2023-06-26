package org.androsovich.cars.services.impl;

import org.androsovich.cars.entities.Car;
import org.androsovich.cars.entities.Detail;
import org.androsovich.cars.exceptions.DetailNotFoundException;
import org.androsovich.cars.services.DetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DetailServiceImplTest {
    @Autowired
    DetailService detailService;

    @Test
    void testSaveMethod() {
        Detail detail = new Detail();
        Detail resultDetail = detailService.save(detail);
        assertNotNull(resultDetail);
        assertNotNull(resultDetail.getId());
    }

    @Test
    @Transactional
    void testDeleteByIdExpectedExceptionDetailNotFound() {
        Detail detail = detailService.save(new Detail("A1", "AA1", null));
        detailService.delete(detail.getId());
        assertThatThrownBy(() -> detailService.findById(1L)).isInstanceOf(DetailNotFoundException.class);
    }

    @Test
    @Transactional
    void testFindById() {
        Detail detail_1 = detailService.save(new Detail("A", "AA", null));
        Detail detail_2 = detailService.save(new Detail("B", "BB",null));

        assertThat(detailService.findById(detail_1.getId()).getSerialNumber()).isEqualTo("AA");
        assertThat(detailService.findById(detail_2.getId()).getName()).isEqualTo("B");
    }
}