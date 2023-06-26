package org.androsovich.drivers.services;

import org.androsovich.drivers.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface DriverService {
    @Transactional
    Page<Driver> getAll(Pageable pageable);

    Page<Driver> getAll();

    Driver findById(Long id);

    Driver save(Driver driver);

    void deleteById(Long id);
}
