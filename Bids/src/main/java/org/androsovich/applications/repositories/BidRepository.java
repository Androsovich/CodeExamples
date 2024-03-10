package org.androsovich.applications.repositories;

import org.androsovich.applications.entities.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Page<Bid> findAllByStatusIn(List<String> statuses, Pageable pageable);

    Page<Bid> findAllByStatusInAndUserLastName(List<String> statuses, String userLastName, Pageable pageable);

    Page<Bid> findAllByUserId(Long ownerId, Pageable pageable);
}
