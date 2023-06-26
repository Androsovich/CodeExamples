package org.androsovich.cars.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.cars.dto.*;
import org.androsovich.cars.dto.assemblers.CarModelAssembler;
import org.androsovich.cars.entities.Car;
import org.androsovich.cars.entities.Detail;
import org.androsovich.cars.services.CarService;
import org.androsovich.cars.services.DetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping(value = "/v1/cars", produces = "application/hal+json")
public class CarController {
    @Autowired
    private CarModelAssembler carModelAssembler;

    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private DetailService detailService;

    @GetMapping(params = {"page", "size",})
    @Operation(summary = "Get cars  paginated : param - page, param - size, param - field")
    public PagedModel<CarResponse> getPageCars(@RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                               @RequestParam(value = "size", defaultValue = "20") @Min(0) @Max(100) int size,
                                               @RequestParam(defaultValue = "vin") String field) {
        return pagedResourcesAssembler.toModel(carService.getAll(PageRequest.of(page, size, Sort.by(field))), carModelAssembler);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get car by id: param - id")
    public CarResponse getCar(@PathVariable Long id) {
        return carModelAssembler.toModel(carService.findById(id));
    }

    @GetMapping("/{id}/detail")
    @Operation(summary = "Get all car details by car id.")
    public List<Detail> getDetailsByCarId(@PathVariable Long id) {
        return carService.findById(id, false).getDetails();
    }

    @PostMapping(consumes = "application/hal+json")
    @Operation(summary = "Save a new car.")
    public void save(@RequestBody CarRequest carRequest) {
        carService.save(modelMapper.map(carRequest, Car.class));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete car by id.")
    public void delete(@PathVariable("id") Long id) {
        carService.deleteById(id);
    }

    /**
     * @param detailRequest a new or existing detail to be installed in the car
     * @param carId     id of the car where the detail will be installed
     */
    @PutMapping("/{id}/detail")
    @Operation(summary = "Update car(added detail)")
    public void addDetail(@RequestBody DetailRequest detailRequest, @PathVariable(name = "id") Long carId) {
        log.info("added detail for car with id = {}: detail to adding {}", carId, detailRequest);
        carService.addDetail(modelMapper.map(detailRequest, Detail.class), carId);
    }

    @PutMapping("/{id}/detail/{detail_id}")
    @Operation(summary = "Replace detail for car: param delete_id - id detail will be delete, param id - id car")
    public void replaceDetail(@RequestBody DetailRequest detailRequest,
                              @PathVariable(name = "id") Long carId,
                              @PathVariable(name = "detail_id") Long detailId) {
        log.info("replace detail for car with id = {}: detail to adding {}, detail_id to removing : {}", carId, detailRequest, detailId);
        detailService.delete(detailId);
        carService.addDetail(modelMapper.map(detailRequest, Detail.class), carId);
    }

    @PutMapping("/{id}/driver")
    @Operation(summary = "Add driver to car: param id - id car, requestBody - driver_Id.")
    public void addDriver(@RequestBody Driver driver, @PathVariable(name = "id") Long carId) {
        carService.addDriver(driver.getId(), carId);
    }
}