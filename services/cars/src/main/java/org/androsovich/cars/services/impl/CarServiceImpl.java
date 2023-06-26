package org.androsovich.cars.services.impl;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.cars.entities.Car;
import org.androsovich.cars.entities.Detail;
import org.androsovich.cars.exceptions.CarNotFoundException;
import org.androsovich.cars.repositories.CarRepository;
import org.androsovich.cars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    @Transactional
    public Car save(Car inputCar) {
        Car car = saveCar(inputCar);
        log.info("Car {} is saved", car);
        return car;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        carRepository.deleteById(id);
        log.info("Car is delete by id = " + id);
    }

    @Override
    @Transactional
    public void addDetail(Detail detail, Long carId) {
        Car car = findById(carId);
        car.getDetails().add(detail);
        saveCar(car);
        log.info("detail " + detail + " successfully added to Car");
    }

    @Override
    @Transactional
    public Page<Car> getAll() {
        return getAll(Pageable.unpaged());
    }

    @Override
    @Transactional
    public Page<Car> getAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Car findById(Long id) {
        return findById(id, true);
    }

    /**
     * @param isLazyFetchChildEntities If false fetch all child entities.
     */
    @Override
    @Transactional
    public Car findById(@NotNull Long id, boolean isLazyFetchChildEntities) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));

        if (!isLazyFetchChildEntities) {
            car.getDetails().size();
        }
        return car;
    }

    @Transactional
    private Car saveCar(@NotNull Car car) {
        car.getDetails().forEach(detail -> detail.setCar(car));
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public void addDriver(@NotNull Long driverId, @NotNull Long carId) {
        if (carRepository.setDriverToCar(driverId, carId) == 0) {
            throw new CarNotFoundException(carId);
        }
    }
}