package com.epam.summer.lab.services;

import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.entities.User;

import java.util.List;

public interface UserService {

    User getAuthenticationUser();

    User save(UserDto userDto);

    List<UserDto> getUsersByProjectId(Long projectId);

    List<UserDto> getUsersNotInProject(Long projectId);

    List<User> findAll();
}