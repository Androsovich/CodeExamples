package org.androsovich.cars.dto.assemblers;

import org.androsovich.cars.controllers.DetailController;
import org.androsovich.cars.dto.DetailResponse;
import org.androsovich.cars.entities.Detail;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DetailModelAssembler implements RepresentationModelAssembler<Detail, DetailResponse> {
    @Override
    public DetailResponse toModel(Detail detail) {
        return getDetailDtoFromDetail(detail, new ModelMapper());
    }

    @Override
    public CollectionModel<DetailResponse> toCollectionModel(Iterable<? extends Detail> details) {
        ModelMapper modelMapper = new ModelMapper();
        List<DetailResponse> driverDTOs = new ArrayList<>();

        for (Detail driver : details) {
            DetailResponse driverDTO = getDetailDtoFromDetail(driver, modelMapper);
            driverDTOs.add(driverDTO);
        }
        return CollectionModel.of(driverDTOs);
    }

    private DetailResponse getDetailDtoFromDetail(@NonNull Detail detail, @NonNull ModelMapper mapper) {
        DetailResponse detailResponse = mapper.map(detail, DetailResponse.class);
        detailResponse.add(linkTo(methodOn(DetailController.class).getDetail(detailResponse.getId())).withSelfRel());
        return detailResponse;
    }
}
