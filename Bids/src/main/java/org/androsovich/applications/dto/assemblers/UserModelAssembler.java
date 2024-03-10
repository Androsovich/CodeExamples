package org.androsovich.applications.dto.assemblers;

import jakarta.validation.constraints.NotNull;
import org.androsovich.applications.controllers.UserController;
import org.androsovich.applications.dto.user.UserResponse;
import org.androsovich.applications.entities.User;
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
public class UserModelAssembler implements RepresentationModelAssembler<User, UserResponse> {


        @Override
        public @NotNull CollectionModel<UserResponse>  toCollectionModel(Iterable<? extends User> users) {
            ModelMapper modelMapper = new ModelMapper();
            List<UserResponse> userResponses = new ArrayList<>();

            for (User user : users) {
                UserResponse userResponse = getUserDtoFromUser(user, modelMapper);
                userResponses.add(userResponse);
            }
            return CollectionModel.of(userResponses);
        }

        @Override
        public @NotNull UserResponse toModel(@NotNull User user) {
            return getUserDtoFromUser(user, new ModelMapper());
        }

        private UserResponse getUserDtoFromUser(User user, @NonNull ModelMapper mapper) {
            UserResponse userDto = mapper.map(user, UserResponse.class);
            userDto.add(linkTo(UserController.class).withRel("/v1/users"));
            userDto.add(linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel());
            return userDto;
        }
}
