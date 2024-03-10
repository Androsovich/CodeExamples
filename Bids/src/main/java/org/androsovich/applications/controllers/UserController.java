package org.androsovich.applications.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.androsovich.applications.dto.assemblers.UserModelAssembler;
import org.androsovich.applications.dto.role.RoleRequest;
import org.androsovich.applications.dto.user.UserResponse;
import org.androsovich.applications.entities.Role;
import org.androsovich.applications.entities.User;
import org.androsovich.applications.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1/users", produces = "application/hal+json")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;
    private final ModelMapper modelMapper;

    @GetMapping(params = {"page", "size"})
    @Operation(summary = "Get users paginated : param - page, param - size. Only user role - ADMIN")
    public PagedModel<UserResponse> getPageUsers(@RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                                 @RequestParam(value = "size", defaultValue = "5") @Min(0) @Max(100) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pagedResourcesAssembler.toModel(userService.getAll(pageable), userModelAssembler);
    }

    @GetMapping
    @Operation(summary = "Get all users.  Only user role - ADMIN")
    public PagedModel<UserResponse> getUsers() {
        return pagedResourcesAssembler.toModel(userService.getAll(), userModelAssembler);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID. Only user role - ADMIN")
    public UserResponse one(@PathVariable Long id) {
        return userModelAssembler.toModel(userService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Grant rights user - OPERATOR.  Only user role - ADMIN, and available for users with role -USER Request body example - ROLE_OPERATOR")
    public void grantRights(@PathVariable(name = "id") Long userId, @RequestBody RoleRequest roleRequest) {
        Role role = modelMapper.map(roleRequest, Role.class);
        userService.grantRightsUser(userId, role);
    }
}
