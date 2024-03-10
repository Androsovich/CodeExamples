package org.androsovich.applications.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.applications.dto.assemblers.BidModelAssembler;
import org.androsovich.applications.dto.bid.BidRequest;
import org.androsovich.applications.dto.bid.BidResponse;
import org.androsovich.applications.entities.Bid;
import org.androsovich.applications.entities.embeddeds.Phone;
import org.androsovich.applications.entities.enums.StatusType;
import org.androsovich.applications.feign.clients.PhoneClient;
import org.androsovich.applications.security.AuthenticationFacade;
import org.androsovich.applications.services.BidService;
import org.androsovich.applications.utils.PhoneUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.androsovich.applications.constants.Constants.CREATE;
import static org.androsovich.applications.entities.enums.StatusType.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/bids", produces = "application/hal+json")
public class BidController {
    private final PhoneClient phoneClient;
    private final BidService bidService;
    private final BidModelAssembler bidModelAssembler;
    private final PagedResourcesAssembler<Bid> pagedResourcesAssembler;
    private final AuthenticationFacade authenticationFacade;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "create bid  and sent operator: param status = SENT or make a DRAFT. Phone format - \"[\\\"+7 911 243-45-76\\\"]\"")
    public void create(@RequestBody BidRequest bidRequest, @RequestParam(defaultValue = "DRAFT") String status) {
        log.info("create bid : - {} , status - {} ",  bidRequest, status);

        Bid bid = modelMapper.map(bidRequest, Bid.class, CREATE);
        StatusType statusType = SENT.name().equalsIgnoreCase(status) ? SENT : DRAFT;
        Phone phone = PhoneUtils.processingResponse(phoneClient.checkPhone(bidRequest.getPhone()));

        bid.setPhone(phone);
        bid.setStatus(statusType);
        bidService.create(bid);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','USER')")
    @Operation(summary = "update status user - to SENT, operator - to ACCEPTED, REJECTED")
    public void updateStatus(@PathVariable(name = "id") Long id, @NotNull @RequestParam String status) {
        bidService.updateStatusById(StatusType.valueOf(status), id);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update the draft bid. Only user role - USER")
    public void update(@RequestBody BidRequest bidRequest) {
       bidService.update(modelMapper.map(bidRequest, Bid.class));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all bids a user. Only user role - USER. Available bid status - ACCEPTED, REJECTED, SENT, DRAFT")
    public PagedModel<BidResponse> getAllUserBids(@RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                                  @RequestParam(value = "size", defaultValue = "5") @Min(0) @Max(100) int size,
                                                  @RequestParam(defaultValue = "createdTime") String field,
                                                  @RequestParam(defaultValue = "DESC") String sortDirection) {
        final Long userId = authenticationFacade.getPrincipalId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), field));
        return pagedResourcesAssembler.toModel(bidService.findAllByUserId(userId, pageable), bidModelAssembler);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    @Operation(summary = "Get all viewable bids for a specific user role. OPERATOR - only status SENT. ADMIN - SENT, ACCEPTED, REJECTED")
    public PagedModel<BidResponse> getAllAvailableBids(@RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                                       @RequestParam(value = "size", defaultValue = "5") @Min(0) @Max(100) int size,
                                                       @RequestParam(defaultValue = "createdTime") String sortedField,
                                                       @RequestParam(defaultValue = "DESC") String sortDirection,
                                                       @RequestParam (name = "filtered", required = false) String filteredUserFullName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortedField));
        return pagedResourcesAssembler.toModel(bidService.findAllByStatusIn(pageable, Optional.ofNullable(filteredUserFullName)), bidModelAssembler);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    @Operation(summary = "View the application for the ID. OPERATOR - only status SENT. ADMIN - SENT, ACCEPTED, REJECTED")
    public BidResponse one(@PathVariable(name = "id") Long id) {
       return bidModelAssembler.toModel(bidService.findById(id));
    }
}
