package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.entities.Role;
import com.epam.summer.lab.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "password", ignore = true)
    public abstract UserDto userToUserDto(User user);


    public UserDto userToUserDtoWithRole(User user) {
        UserDto userDto = userToUserDto(user);
        String[] roles =
                user
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .toArray(String[]::new);
        userDto.setRole(roles);
        return userDto;
    }

    public List<UserDto> toUserDto(Collection<User> users) {
        return
                users
                        .stream()
                        .map(this::userToUserDtoWithRole)
                        .collect(Collectors.toList());
    }

    public User userDtoToUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setBirthday(userDto.getBirthday());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        List<Role> roles = Arrays.stream(userDto.getRole())
                .map(id -> {
                            Role role = new Role();
                            role.setId(Long.valueOf(id));
                            return role;
                        }
                )
                .collect(Collectors.toList());

        user.setRoles(roles);
        return user;
    }
}