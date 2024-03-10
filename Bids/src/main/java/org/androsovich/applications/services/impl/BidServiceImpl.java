package org.androsovich.applications.services.impl;

import lombok.AllArgsConstructor;
import org.androsovich.applications.entities.Bid;
import org.androsovich.applications.entities.Privilege;
import org.androsovich.applications.entities.enums.OperationType;
import org.androsovich.applications.entities.enums.StatusType;
import org.androsovich.applications.exceptions.BidNotFoundException;
import org.androsovich.applications.exceptions.IllegalStatusException;
import org.androsovich.applications.repositories.BidRepository;
import org.androsovich.applications.services.BidService;
import org.androsovich.applications.services.UserService;
import org.androsovich.applications.utils.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static org.androsovich.applications.constants.Constants.*;
import static org.androsovich.applications.entities.enums.OperationType.READ;
import static org.androsovich.applications.entities.enums.OperationType.UPDATE_STATUS_TO;
import static org.androsovich.applications.entities.enums.StatusType.DRAFT;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {
    private final UserService userService;
    private final BidRepository bidRepository;

    @Override
    @Transactional
    public void updateStatusById(StatusType status, Long id) {
        Bid bid = bidRepository.findById(id).orElseThrow(() -> new BidNotFoundException(BID_NOT_FOUND_ID + id));
        List<Privilege> privileges = userService.getPrivilegesForPrincipal();

        boolean isPermitted = UserUtils.parsePrivileges(privileges).get(UPDATE_STATUS_TO.name())
                .stream()
                .anyMatch(status.name()::equals);

        if ((status.prevStatus() == bid.getStatus()) && isPermitted) {
            bid.setStatus(status);
        } else {
            throw new IllegalStatusException(INVALID_STATUS_MESSAGE + bid.getStatus());
        }
    }

    @Override
    @Transactional
    public void update(Bid inputBid) {
        Bid bid = bidRepository.findById(inputBid.getId()).orElseThrow(() -> new BidNotFoundException(BID_NOT_FOUND_ID + inputBid.getId()));

        if (DRAFT != bid.getStatus()) {
            throw new IllegalStatusException(INVALID_STATUS_MESSAGE + bid.getStatus());
        }
        bid.setName(inputBid.getName());
        bid.setText(inputBid.getText());
        bid.setPhone(inputBid.getPhone());

        bidRepository.save(bid);
    }

    @Override
    @Transactional
    public void create(Bid bid) {
        bidRepository.save(bid);
    }

    @Override
    @Transactional
    public Page<Bid> findAllByUserId(Long userId, Pageable pageable) {
        return bidRepository.findAllByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public Page<Bid> findAllByStatusIn(Pageable pageable, Optional<String> userLastName) {
        List<String> statuses = getAvailableStatuses(READ);
        return userLastName.isPresent() ?
                findAllByStatusInAndUserLastName(statuses, pageable, userLastName.get()) : findAllByStatusIn(statuses, pageable);
    }

    @Override
    @Transactional
    public Bid findById(Long id) {
        Bid bid = bidRepository.findById(id).orElseThrow(() -> new BidNotFoundException(BID_NOT_FOUND_ID + id));
        List<String> statuses = getAvailableStatuses(READ);
        boolean isPermitted = statuses.contains(bid.getStatus().name());
        if(!isPermitted){
            throw new IllegalStatusException(INVALID_STATUS_MESSAGE + bid.getStatus());
        }
        return bid;
    }

    private Page<Bid> findAllByStatusInAndUserLastName(List<String> statuses, Pageable pageable, String userLastName) {
        return bidRepository.findAllByStatusInAndUserLastName(statuses, userLastName, pageable);
    }

    private Page<Bid> findAllByStatusIn(List<String> statuses, Pageable pageable) {
        return bidRepository.findAllByStatusIn(statuses, pageable);
    }

    private List<String> getAvailableStatuses(OperationType operationType) {
        List<Privilege> privileges = userService.getPrivilegesForPrincipal();
         return UserUtils.getAvailableStatusesForUser(privileges, operationType);
    }
}
