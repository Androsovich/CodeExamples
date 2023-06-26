package org.androsovich.cars.services;

import org.androsovich.cars.entities.Detail;

public interface DetailService {

   Detail save(Detail detail);

   void delete(long id);

   Detail findById(Long id);
}
