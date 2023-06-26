package org.androsovich.cars.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.cars.dto.DetailResponse;
import org.androsovich.cars.dto.assemblers.DetailModelAssembler;
import org.androsovich.cars.services.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/v1/details", produces = "application/hal+json")
public class DetailController {
    @Autowired
    DetailModelAssembler detailModelAssembler;

    @Autowired
    DetailService detailService;

    @GetMapping("/{id}")
    @Operation(summary = "Get detail by id. param - id")
    public DetailResponse getDetail(@PathVariable("id") Long id) {
        return detailModelAssembler.toModel(detailService.findById(id));
    }
}