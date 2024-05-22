package org.androsovich.accounts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.androsovich.accounts.dto.assemblers.UserModelAssembler;
import org.androsovich.accounts.dto.user.UserRequest;
import org.androsovich.accounts.dto.user.UserResponse;
import org.androsovich.accounts.entities.User;
import org.androsovich.accounts.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/users", produces = "application/hal+json")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Get users paginated with filtering by firstName, lastName and sorting")
    public PagedModel<UserResponse> getUsersWithFilterByFirstNameLastName(@RequestParam(defaultValue = "") String filterFirstName,
                                                                          @RequestParam(defaultValue = "") String filterLastName,
                                                                          @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                          @RequestParam(defaultValue = "20") @Min(0) @Max(100) int size,
                                                                          @RequestParam(defaultValue = "DESC") String sortDirection,
                                                                          @RequestParam(defaultValue = "firstName") String sortedField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortedField));
        Page<User> usersPage = userService.findAllWithFilteringAndSorting(filterFirstName, filterLastName, pageable);
        return pagedResourcesAssembler.toModel(usersPage, userModelAssembler);
    }

    @GetMapping("/{birthDay}")
    @Operation(summary = "Get users paginated with filtering by birthDay and sorting")
    public PagedModel<UserResponse> getUsersByBirthdayGreaterThanEqual(@PathVariable LocalDate birthDay,
                                                                       @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                       @RequestParam(defaultValue = "20") @Min(0) @Max(100) int size,
                                                                       @RequestParam(defaultValue = "DESC") String sortDirection,
                                                                       @RequestParam(defaultValue = "birthDay") String sortedField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortedField));
        Page<User> usersPage = userService.findAllByBirthdayGreaterThanEqual(birthDay, pageable);
        return pagedResourcesAssembler.toModel(usersPage, userModelAssembler);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID.")
    public UserResponse one(@PathVariable Long id) {
        return userModelAssembler.toModel(userService.findById(id));
    }

    @GetMapping("/phone/{phone}")
    @Operation(summary = "Get user by Phone.")
    public UserResponse oneByPhone(@PathVariable String phone) {
        return userModelAssembler.toModel(userService.findByPhone(phone));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by Email.")
    public UserResponse oneByEmail(@PathVariable String email) {
        return userModelAssembler.toModel(userService.findByEmail(email));
    }

    @PutMapping
    @Operation(summary = "Update user phone or email. An empty field ignores the update.")
    public UserResponse update(@RequestBody UserRequest userRequest) {
        return userModelAssembler.toModel(userService.update(userRequest));
    }
}
