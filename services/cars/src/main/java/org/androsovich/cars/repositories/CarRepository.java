package org.androsovich.cars.repositories;

import org.androsovich.cars.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Modifying
    @Query("update Car car set car.driverId = :driverId where car.id = :id")
    int setDriverToCar(@Param("driverId") Long driverId, @Param("id") Long id);

}
