package com.epam.summer.lab.controllers;

import com.epam.summer.lab.dto.TaskDto;
import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.dto.transfer.Delete;
import com.epam.summer.lab.dto.transfer.New;
import com.epam.summer.lab.dto.transfer.Update;
import com.epam.summer.lab.dto.transfer.UpdateStatus;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.services.ProjectService;
import com.epam.summer.lab.services.TaskService;
import com.epam.summer.lab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping
public class TaskController {
    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping("/api/task/details/{id}")
    public ModelAndView showTaskInfo(@PathVariable("id") Long taskId, ModelAndView modelAndView) {
        Task task = taskService.findTaskById(taskId);
        modelAndView.addObject("task_info", task);
        modelAndView.setViewName("fragments/user/user_task");
        return modelAndView;
    }

    @GetMapping("/api/projects/{id}")
    public ModelAndView showProjectTasks(@PathVariable("id") Long projectId, ModelAndView modelAndView) {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        modelAndView.addObject("tasks", tasks);
        modelAndView.setViewName("fragments/project/project::project_tasks");
        return modelAndView;
    }

    @GetMapping(value = "/security/task/create/{id}")
    public String showTaskCreate(@PathVariable("id") Long projectId, Model model) {
        List<UserDto> usersDto = userService.getUsersByProjectId(projectId);

        model.addAttribute("project_id", projectId);
        model.addAttribute("users", usersDto);

        return "fragments/task/create_task";
    }

    @GetMapping(value = "/security/task/update/{id}")
    public ModelAndView showTaskUpdate(@PathVariable("id") Long taskId, ModelAndView modelAndView) {
        Task task = taskService.findTaskById(taskId);

        final Long projectId = task.getProject().getId();
        final Long userId = task.getExecutor().getId();

        List<UserDto> usersDto = userService.getUsersByProjectId(projectId);

        modelAndView.addObject("task", task);
        modelAndView.addObject("executorId", userId);
        modelAndView.addObject("users", usersDto);
        modelAndView.setViewName("fragments/task/update_task");
        return modelAndView;
    }


    @RequestMapping(value = "/api/task/update/status", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> updateStatus(@Validated(UpdateStatus.class) @RequestBody TaskDto taskDto) {
        return update(taskDto);
    }

    @RequestMapping(value = "/api/task/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> update(@Validated(Update.class) @RequestBody TaskDto taskDto) {
        Task task = taskService.update(taskDto);
        return Objects.nonNull(task) ? new ResponseEntity<>(OK) : new ResponseEntity<>(BAD_REQUEST);
    }


    @RequestMapping(value = "/security/task/delete", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> delete(@Validated(Delete.class) @RequestBody TaskDto taskDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        taskService.delete(taskDto.getId());

        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "/security/task/save/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> save(@Validated(New.class) @RequestBody TaskDto taskDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        taskService.save(taskDto);

        return new ResponseEntity<>(OK);
    }
}