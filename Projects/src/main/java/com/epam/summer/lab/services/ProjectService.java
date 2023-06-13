package com.epam.summer.lab.services;

import com.epam.summer.lab.dto.ProjectDto;
import com.epam.summer.lab.entities.Project;
import com.epam.summer.lab.entities.User;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();

    Project findById(Long projectId);

    Project update(ProjectDto projectDto);

    void delete(Long projectId);

    void deleteUser(Long projectId, Long userId);

    void saveUser(Long projectId, Long userId);

    Project save(ProjectDto projectDto, User user);

    List<Project> findAllByUsersId(Long id);
}