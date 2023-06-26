package org.androsovich.drivers.repositories;

import org.androsovich.drivers.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    @Query(value = "SELECT * FROM drivers " +
            "WHERE extract(MONTH FROM birthday) = :m " +
            "AND extract(DAY FROM birthday) = :d",
            nativeQuery = true)
    List<Driver> findDriverByBirthday(@Param("m") int month, @Param("d") int day);
}
