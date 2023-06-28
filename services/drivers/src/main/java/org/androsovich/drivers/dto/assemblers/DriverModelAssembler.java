package org.androsovich.drivers.dto.assemblers;

import org.androsovich.drivers.controllers.DriverController;
import org.androsovich.drivers.dto.DriverResponse;
import org.androsovich.drivers.entities.Driver;
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
public class DriverModelAssembler implements RepresentationModelAssembler<Driver, DriverResponse> {
    @Override
    public CollectionModel<DriverResponse> toCollectionModel(Iterable<? extends Driver> drivers) {
        ModelMapper modelMapper = new ModelMapper();
        List<DriverResponse> driverResponses = new ArrayList<>();

        for (Driver driver : drivers) {
            DriverResponse driverResponse = getDriverDtoFromDriver(driver, modelMapper);
            driverResponses.add(driverResponse);
        }
        return CollectionModel.of(driverResponses);
    }

    @Override
    public DriverResponse toModel(Driver driver) {
        return getDriverDtoFromDriver(driver, new ModelMapper());
    }

    private DriverResponse getDriverDtoFromDriver(@NonNull Driver driver, @NonNull ModelMapper mapper) {
        DriverResponse carDto = mapper.map(driver, DriverResponse.class);
        carDto.add(linkTo(DriverController.class).withRel("/v1/cars"));
        carDto.add(linkTo(methodOn(DriverController.class).one(driver.getId())).withSelfRel());
        return carDto;
    }
}
