package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.dto.ProjectDto;
import com.epam.summer.lab.entities.Project;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.exceptions.ProjectNotFoundException;
import com.epam.summer.lab.repositories.ProjectRepository;
import com.epam.summer.lab.repositories.TaskRepository;
import com.epam.summer.lab.services.ProjectService;
import com.epam.summer.lab.utils.mapper.ProjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.summer.lab.constants.Constants.*;


@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;


    @Autowired
    ProjectMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        List<Project> projects = projectRepository.findAll();
        projects
                .forEach(project -> {
                    project.getTasks().size();
                    project.getProjectUsers().size();
                    project.getUser();
                });
        return projects;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAllByUsersId(Long id) {
        Optional<List<Project>> optionalProjects = Optional.of(projectRepository.findAllByProjectUsersId(id));
        List<Project> projects =
                optionalProjects
                        .orElseThrow(() -> new ProjectNotFoundException(PROJECTS_NOT_FOUND_USER_ID + id));

        return
                projects
                        .stream()
                        .filter(project -> !project.getStatus().equals(STATUS_COMPLETED))
                        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Project findById(Long projectId) {
        return
                projectRepository
                        .findById(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND_ID + projectId));
    }

    @Override
    @Transactional
    public Project update(ProjectDto projectDto) {
        Project project = findById(projectDto.getId());
        mapper.updateProject(projectDto, project);

        return projectRepository.save(project);
    }

    public Project save(ProjectDto projectDto, User user) {
        Project project = mapper.projectDtoToProject(projectDto);
        project.setUser(user);

        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public void delete(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    @Transactional
    public void deleteUser(Long projectId, Long userId) {
        Project project = projectRepository.getOne(projectId);

        Optional<User> user =
                project
                        .getProjectUsers()
                        .stream()
                        .filter(value -> value.getId().equals(userId))
                        .findFirst();

        Optional<Task> task =
                project
                        .getTasks()
                        .stream()
                        .filter(value -> value.getExecutor().getId().equals(userId))
                        .findFirst();

        user.ifPresent(value -> project.getProjectUsers().remove(value));
        task.ifPresent(value -> taskRepository.delete(value));

        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void saveUser(Long projectId, Long userId) {
        projectRepository.saveUserToProject(projectId, userId);
    }
}