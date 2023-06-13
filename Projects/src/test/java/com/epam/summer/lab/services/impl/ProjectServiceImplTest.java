package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.ProjectDto;
import com.epam.summer.lab.entities.Project;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.exceptions.ProjectNotFoundException;
import com.epam.summer.lab.exceptions.UserException;
import com.epam.summer.lab.repositories.UserRepository;
import com.epam.summer.lab.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class ProjectServiceImplTest {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void testFindAll() {
        List<Project> projects = projectService.findAll();
        List<List<Task>> tasksList = projects.stream().map(Project::getTasks).collect(Collectors.toList());
        List<List<User>> usersList = projects.stream().map(Project::getProjectUsers).collect(Collectors.toList());
        assertNotNull(projects);
        assertNotNull(tasksList);
        assertNotNull(usersList);
    }

    @Test
    @Transactional
    void testFindById() {
        Project project = projectService.findById(1L);
        assertNotNull(project);
    }

    @Test
    @Transactional
    void testUpdate() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(1L);
        projectDto.setTitle("COMPLETED#####");
        projectDto.setStatus("COMPLETED");

        Project project = projectService.update(projectDto);

        assertEquals(project.getStatus(), projectDto.getStatus());
        assertEquals(project.getTitle(), projectDto.getTitle());
        assertEquals(project.getId(), projectDto.getId());
    }

    @Test
    @Transactional
    void testDelete() {
        Project project = projectService.findById(1L);
        projectService.delete(1L);
        assertThrows(ProjectNotFoundException.class, () -> projectService.findById(1L));

    }

    @Test
    @Transactional
    void testDeleteUser() {
        Project project = projectService.findById(1L);
        User user =
                project
                        .getProjectUsers()
                        .stream()
                        .findAny()
                        .orElseThrow(UserException::new);
        projectService.deleteUser(1L, user.getId());

        assertFalse(project.getProjectUsers().contains(user));
    }

    @Test
    @Transactional
    void testSaveUser() {
        User user = new User();
        user.setId(45L);
        user = userRepository.save(user);
        Project project = projectService.findById(1L);

        projectService.saveUser(1L, user.getId());
        assertTrue(project.getProjectUsers().contains(user));
    }

    @Test
    @Transactional
    void testFindAllByUserId() {
        List<Project> projects = projectService.findAllByUsersId(2L);
        assertNotNull(projects);
        assertTrue(projects.size() > 0);
    }

    @Test
    @Transactional
    void testSave() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setTitle("COMPLETED#####");
        projectDto.setStatus("INACTIVE");

        User user = new User();
        user.setId(2L);

        Project project = projectService.save(projectDto, user);

        assertThat(Objects.nonNull(project)).isTrue();
        assertThat(Objects.nonNull(project.getId())).isTrue();
    }
}