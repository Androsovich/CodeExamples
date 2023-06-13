package com.epam.summer.lab.controllers;

import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.services.TaskService;
import com.epam.summer.lab.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class NavigateController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @RequestMapping("/api/user-info")
    public String showUserInfo(Model model) {
        User user = userService.getAuthenticationUser();
        List<Task> tasks = taskService.getTasksByUser(user);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        return "user_info";
    }
}