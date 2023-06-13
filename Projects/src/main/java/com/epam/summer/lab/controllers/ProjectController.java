package com.epam.summer.lab.controllers;

import com.epam.summer.lab.dto.ProjectDto;
import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.dto.transfer.Delete;
import com.epam.summer.lab.dto.transfer.New;
import com.epam.summer.lab.dto.transfer.Update;
import com.epam.summer.lab.entities.Project;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.services.ProjectService;
import com.epam.summer.lab.services.TaskService;
import com.epam.summer.lab.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping
@AllArgsConstructor
public class ProjectController {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @GetMapping("/api/projects")
    public String showProjectsUser(Model model) {
        User user = userService.getAuthenticationUser();
        List<Project> projects = projectService.findAllByUsersId(user.getId());
        model.addAttribute("projects", projects);

        return "fragments/user/user_projects";
    }

    @GetMapping(value = "/security/project/create")
    public String showCreate() {
        return "/fragments/project/create_project";
    }

    @RequestMapping(value = "/security/project/delete/user")
    public ResponseEntity<Project> deleteUser(@RequestParam("project_id") Long projectId,
                                              @RequestParam("user_id") Long userId) {
        projectService.deleteUser(projectId, userId);

        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "/security/project/save/user")
    public ResponseEntity<Project> saveUser(@RequestParam("project_id") Long projectId,
                                            @RequestParam("user_id") Long userId) {
        projectService.saveUser(projectId, userId);

        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "/security/project/add/user")
    public String showAddUserView(@RequestParam("project_id") Long projectId, Model model) {
        List<UserDto> usersDto = userService.getUsersNotInProject(projectId);
        model.addAttribute("users", usersDto);
        model.addAttribute("project_id", projectId);
        return "fragments/project/project::add_user_project";
    }

    @GetMapping("/security/project/update/{id}")
    public String showUpdateView(@PathVariable("id") Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        return "fragments/project/update_project";
    }

    @GetMapping("/security/projects")
    public String showAllProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "admin";
    }

    @RequestMapping(value = "/security/project/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Project> update(@Validated(Update.class) @RequestBody ProjectDto projectDto) {
        Project project = projectService.update(projectDto);

        return Objects.nonNull(project) ? new ResponseEntity<>(OK) : new ResponseEntity<>(BAD_REQUEST);
    }

    @RequestMapping(value = "/security/project/delete", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Project> delete(@Validated(Delete.class)
                                          @RequestBody ProjectDto projectDto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        projectService.delete(projectDto.getId());

        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "/security/project/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Project> save(@Validated(New.class) @RequestBody ProjectDto projectDto) {

        User user = userService.getAuthenticationUser();
        Project project = projectService.save(projectDto, user);

        return Objects.nonNull(project) ? new ResponseEntity<>(OK) : new ResponseEntity<>(BAD_REQUEST);
    }
}