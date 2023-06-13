package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.dto.TaskDto;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.exceptions.TaskException;
import com.epam.summer.lab.exceptions.TaskNotFoundException;
import com.epam.summer.lab.repositories.TaskRepository;
import com.epam.summer.lab.services.TaskService;
import com.epam.summer.lab.utils.mapper.TaskMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.summer.lab.constants.Constants.*;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    TaskMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.getExecutor();
                    task.getProject();
                    task.getComments().size();
                    return task;
                })
                .orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_BY_ID + taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTasksByUser(User executor) {
        if (Objects.isNull(executor) || Objects.isNull(executor.getId())) {
            throw new TaskException(INVALID_USER);
        }
        return taskRepository.getTasksByExecutor(executor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTasksByProjectId(Long projectId) {
        Optional<List<Task>> taskList = taskRepository.findTaskByProjectId(projectId);
        return
                taskList
                        .map(tasks -> {
                            tasks.forEach(Task::getExecutor);
                            return tasks;
                        })
                        .orElseThrow(() -> new TaskNotFoundException(TASKS_NOT_FOUND_BY_PROJECT + projectId));
    }

    @Override
    @Transactional
    public Task update(TaskDto taskDto) {
        if (Objects.isNull(taskDto)) {
            throw new TaskException(INVALID_TASK);
        }

        checkAndUpdateEndDate(taskDto);

        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND_BY_ID + taskDto.getId()));
        mapper.setNestedEntities(taskDto, task);
        mapper.updateTaskFromDto(taskDto, task);

        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Task save(TaskDto taskDto) {
        Task task = mapper.taskDtoToTask(taskDto);
        return taskRepository.save(task);
    }

    private void checkAndUpdateEndDate(TaskDto taskDto) {
        final String status = taskDto.getStatus();

        if (Objects.isNull(taskDto.getEndDate()) && Objects.nonNull(status)) {
            if (status.equals(STATUS_COMPLETED)) {
                taskDto.setEndDate(LocalDate.now());
            }
        }
    }
}