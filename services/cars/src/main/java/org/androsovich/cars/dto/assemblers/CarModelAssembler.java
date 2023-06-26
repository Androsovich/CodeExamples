package org.androsovich.cars.dto.assemblers;

import org.androsovich.cars.controllers.CarController;
import org.androsovich.cars.dto.CarResponse;
import org.androsovich.cars.entities.Car;
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
public class CarModelAssembler implements RepresentationModelAssembler<Car, CarResponse> {


    @Override
    public CarResponse toModel(@NonNull Car car) {
        return getCarDtoFromCar(car, new ModelMapper());
    }

    @Override
    public CollectionModel<CarResponse> toCollectionModel(Iterable<? extends Car> cars) {
        ModelMapper modelMapper = new ModelMapper();
        List<CarResponse> carResponses = new ArrayList<>();

        for (Car car : cars) {
            CarResponse carResponse = getCarDtoFromCar(car, modelMapper);
            carResponses.add(carResponse);
        }
        return CollectionModel.of(carResponses);
    }

    private CarResponse getCarDtoFromCar(@NonNull Car car, @NonNull ModelMapper mapper) {
        CarResponse carResponse = mapper.map(car, CarResponse.class);
        carResponse.add(linkTo(CarController.class).withRel("/v1/cars"));
        carResponse.add(linkTo(methodOn(CarController.class).getCar(car.getId())).withSelfRel());
        return carResponse;
    }
}
