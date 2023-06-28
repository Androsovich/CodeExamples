package org.androsovich.drivers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.drivers.dto.DriverResponse;
import org.androsovich.drivers.dto.DriverRequest;
import org.androsovich.drivers.dto.assemblers.DriverModelAssembler;
import org.androsovich.drivers.entities.Driver;
import org.androsovich.drivers.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping(value = "/v1/drivers", produces = "application/hal+json")
public class DriverController {

    @Autowired
    DriverService driverService;

    @Autowired
    DriverModelAssembler driverModelAssembler;

    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping(params = {"page", "size"})
    @Operation(summary = "Get drivers paginated : param - page, param - size")
    public PagedModel<DriverResponse> getPageDrivers(@RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                                     @RequestParam(value = "size", defaultValue = "20") @Min(0) @Max(100) int size,
                                                     @RequestParam(defaultValue = "fullName") String field) {
        return pagedResourcesAssembler.toModel(driverService.getAll(PageRequest.of(page, size, Sort.by(field))), driverModelAssembler);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get driver by id.")
    public DriverResponse one(@PathVariable Long id) {
        return driverModelAssembler.toModel(driverService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Save new driver.")
    public void save(@RequestBody Driver driver) {
        driverService.save(driver);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete driver by id. param - id")
    public void delete(@PathVariable("id") Long id) {
        driverService.deleteById(id);
    }

    @PutMapping("/car/{carId}")
    @Operation(summary = "Connect to car-service and set driver to car.")
    public void setDriverToCar(@RequestBody DriverRequest driverRequest, @PathVariable(name = "carId") Long carId) {
        String serviceUrl = "http://localhost:8080/v1/cars/" + carId + "/driver";
        new RestTemplate().put(serviceUrl, driverRequest);
    }
}
