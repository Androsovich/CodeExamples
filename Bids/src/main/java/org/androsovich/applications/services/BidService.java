package org.androsovich.applications.services;

import org.androsovich.applications.entities.Bid;
import org.androsovich.applications.entities.enums.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BidService {

    void updateStatusById(StatusType status, Long id);

    void update(Bid bid);

    void create(Bid bid);

    Page<Bid> findAllByUserId(Long userId, Pageable pageable);

    Page<Bid> findAllByStatusIn(Pageable pageable, Optional<String> filteredField);

    Bid findById(Long id);
}
