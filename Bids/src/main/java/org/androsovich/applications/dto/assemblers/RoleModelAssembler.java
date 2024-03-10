package org.androsovich.applications.dto.assemblers;

import jakarta.validation.constraints.NotNull;
import org.androsovich.applications.dto.role.RoleResponse;
import org.androsovich.applications.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, RoleResponse> {


    @Override
    public @NotNull CollectionModel<RoleResponse> toCollectionModel(Iterable<? extends Role> roles) {
        ModelMapper modelMapper = new ModelMapper();
        List<RoleResponse> userResponses = new ArrayList<>();

        for (Role role : roles) {
            RoleResponse roleResponse = getRoleDtoFromRole(role, modelMapper);
            userResponses.add(roleResponse);
        }
        return CollectionModel.of(userResponses);
    }

    @Override
    public @NotNull RoleResponse toModel(@NotNull Role role) {
        return getRoleDtoFromRole(role, new ModelMapper());
    }

    private RoleResponse getRoleDtoFromRole(Role role, @NonNull ModelMapper mapper) {
        return mapper.map(role, RoleResponse.class);
    }
}
