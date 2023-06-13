package com.epam.summer.lab.controllers;

import com.epam.summer.lab.dto.TaskDto;
import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.dto.transfer.New;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.services.TaskService;
import com.epam.summer.lab.services.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@Setter
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<User> save(@Validated(New.class) @RequestBody UserDto userDto) {
        User resultUser = userService.save(userDto);
        return Objects.nonNull(resultUser) ? new ResponseEntity<>(OK) : new ResponseEntity<>(BAD_REQUEST);
    }

     @GetMapping(value = "/security/users/all")
    public String showAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "fragments/user/users";
    }

    @RequestMapping(value = "/api/users/view/project")
    public ModelAndView getProjectUsers(@RequestBody TaskDto taskDto, ModelAndView modelAndView) {
        final Task task = taskService.findTaskById(taskDto.getId());
        final Long projectId = task.getProject().getId();

        List<UserDto> users = userService.getUsersByProjectId(projectId);

        modelAndView.addObject("users", users);
        modelAndView.setViewName("fragments/task/task_fragments::list_users");
        return modelAndView;
    }
}