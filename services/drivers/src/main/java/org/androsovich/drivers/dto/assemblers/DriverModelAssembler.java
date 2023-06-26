package org.androsovich.drivers.dto.assemblers;

import org.androsovich.drivers.controllers.DriverController;
import org.androsovich.drivers.dto.DriverDTO;
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
public class DriverModelAssembler implements RepresentationModelAssembler<Driver, DriverDTO> {
    @Override
    public CollectionModel<DriverDTO> toCollectionModel(Iterable<? extends Driver> drivers) {
        ModelMapper modelMapper = new ModelMapper();
        List<DriverDTO> driverDTOs = new ArrayList<>();

        for (Driver driver : drivers) {
            DriverDTO driverDTO = getDriverDtoFromDriver(driver, modelMapper);
            driverDTOs.add(driverDTO);
        }
        return CollectionModel.of(driverDTOs);
    }

    @Override
    public DriverDTO toModel(Driver driver) {
        return getDriverDtoFromDriver(driver, new ModelMapper());
    }

    private DriverDTO getDriverDtoFromDriver(@NonNull Driver driver, @NonNull ModelMapper mapper) {
        DriverDTO carDto = mapper.map(driver, DriverDTO.class);
        carDto.add(linkTo(DriverController.class).withRel("/v1/cars"));
        carDto.add(linkTo(methodOn(DriverController.class).one(driver.getId())).withSelfRel());
        return carDto;
    }
}
