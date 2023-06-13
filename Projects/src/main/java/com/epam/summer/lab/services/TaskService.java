package com.epam.summer.lab.services;

import com.epam.summer.lab.dto.TaskDto;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;

import java.util.List;

public interface TaskService {
    Task findTaskById(Long taskId);

    List<Task> getTasksByUser(User executor);

    List<Task> getTasksByProjectId(Long projectId);

    Task update(TaskDto taskDto);

    void delete(Long id);

    Task save(TaskDto taskDto);
}