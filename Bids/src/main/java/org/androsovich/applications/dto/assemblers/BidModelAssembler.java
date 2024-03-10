package org.androsovich.applications.dto.assemblers;

import jakarta.validation.constraints.NotNull;
import org.androsovich.applications.dto.bid.BidResponse;
import org.androsovich.applications.entities.Bid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BidModelAssembler implements RepresentationModelAssembler<Bid, BidResponse> {
    @Override
    public @NotNull CollectionModel<BidResponse> toCollectionModel(@NotNull Iterable<? extends Bid> bids) {
        return RepresentationModelAssembler.super.toCollectionModel(bids);
    }

    @Override
    public @NotNull BidResponse toModel(@NotNull Bid bid) {
        return BidResponse.builder()
                .id(bid.getId())
                .name(bid.getName())
                .status(bid.getStatus().name())
                .text(bid.getText())
                .phone(bid.getPhone().getPhone())
                .createdTime(bid.getCreatedTime().toString())
                .firstName(bid.getUser().getFirstName())
                .lastName(bid.getUser().getLastName())
                .build();
    }

}