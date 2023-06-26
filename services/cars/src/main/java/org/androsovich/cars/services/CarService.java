package org.androsovich.cars.services;

import org.androsovich.cars.entities.Car;
import org.androsovich.cars.entities.Detail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    Car save(Car car);

    void addDetail(Detail detail, Long carId);

    void deleteById(Long id);

    Page<Car> getAll();

    Car findById(Long id);

    Car findById(Long id, boolean isLazyFetchChildEntities);

    Page<Car> getAll(Pageable pageable);

    void addDriver(Long driverId, Long carId);
}
