package org.androsovich.cars.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.androsovich.cars.entities.Detail;
import org.androsovich.cars.exceptions.DetailNotFoundException;
import org.androsovich.cars.repositories.DetailRepository;
import org.androsovich.cars.services.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DetailServiceImpl implements DetailService {
    @Autowired
    DetailRepository detailRepository;

    @Override
    @Transactional
    public Detail save(Detail sourceDetail) {
        Detail detail = detailRepository.save(sourceDetail);
        log.info("Detail is saved");
        return detail;
    }

    @Override
    @Transactional
    public void delete(long id) {
        detailRepository.deleteById(id);
        log.info("Detail is deleted by id = " + id);
    }

    @Override
    @Transactional
    public Detail findById(Long id) {
        return detailRepository.findById(id).orElseThrow(() -> new DetailNotFoundException(id));
    }
}

