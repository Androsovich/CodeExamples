package org.androsovich.drivers.services.impl;

import org.androsovich.drivers.entities.Driver;
import org.androsovich.drivers.exceptions.DriverNotFoundException;
import org.androsovich.drivers.repositories.DriverRepository;
import org.androsovich.drivers.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.androsovich.drivers.constants.CommonConstants.DRIVER_NOT_FOUND_ID;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Override
    @Transactional
    public Driver findById(Long id) {
        return driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(DRIVER_NOT_FOUND_ID + id));
    }

    @Override
    @Transactional
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    @Transactional
    public Page<Driver> getAll(Pageable pageable) {
        return driverRepository.findAll(pageable);
    }

   @Override
    @Transactional
    public Page<Driver> getAll() {
        return driverRepository.findAll(Pageable.unpaged());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        driverRepository.deleteById(id);
    }
}
