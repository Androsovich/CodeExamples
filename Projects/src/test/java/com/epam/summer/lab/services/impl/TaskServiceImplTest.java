package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.TaskDto;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.exceptions.TaskException;
import com.epam.summer.lab.exceptions.TaskNotFoundException;
import com.epam.summer.lab.repositories.TaskRepository;
import com.epam.summer.lab.services.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class TaskServiceImplTest {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Test
    @DisplayName("test getTasksByUserId success")
    @Transactional
    void getTasksByUserSuccess() {
        User user = new User();
        user.setId(2L);
        List<Task> tasks = taskService.getTasksByUser(user);
        assertEquals(3, tasks.size());
    }

    @ParameterizedTest
    @DisplayName(value = "test getAuthenticationUser is Null expected TaskNotFoundException")
    @NullSource
    @Transactional
    void getTasksByUserIsNullExpectException(Long userId) {
        User user = new User();
        user.setId(userId);
        assertThatThrownBy(() -> taskService.getTasksByUser(user)).isInstanceOf(TaskException.class);
    }

    @Test
    @DisplayName(value = "test GetTasksByProjectId success")
    @Transactional
    void testGetTasksByProjectId() {
        List<Task> tasks = taskService.getTasksByProjectId(2L);
        assertNotNull(tasks);
        assertEquals(3, tasks.size());
    }

    @Test
    @DisplayName(value = "test findTaskById success")
    @Transactional
    void testFindTaskById() {
        Task task = taskService.findTaskById(1L);
        assertNotNull(task);
        assertNotNull(task.getExecutor());
        assertNotNull(task.getComments());
    }

    @ParameterizedTest
    @DisplayName(value = "test findTaskById is wrong id expected TaskNotFoundException")
    @ValueSource(ints = {-1, 0, 158, -3, Integer.MAX_VALUE})
    @Transactional
    void findTaskByIdWrongIdExpectException(long taskId) {
        assertThatThrownBy(() ->taskService.findTaskById(taskId)).isInstanceOf(TaskNotFoundException.class);
    }


    @Test
    @DisplayName(value = "test updateTask success")
    @Transactional
    void updateTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setStatus("COMPLETED");

        Task currentTask = taskService.findTaskById(taskDto.getId());
        assertNotEquals(taskDto.getStatus(), currentTask.getStatus());

        Task resultTask = taskService.update(taskDto);
        assertEquals(resultTask.getStatus(), taskDto.getStatus());
    }

    @ParameterizedTest
    @DisplayName(value = "test updateTask TaskDto=null expected TaskNotFoundException")
    @NullSource
    @Transactional
    void testUpdateTaskNullTaskDtoExpectedException(TaskDto taskDto) {
        assertThatThrownBy(() ->taskService.update(taskDto)).isInstanceOf(TaskException.class);
    }

    @Test
    @DisplayName(value = "test delete task success")
    @Transactional
    void testDelete() {
        Task task = taskRepository.findById(1L).orElseThrow(TaskException::new);
        taskService.delete(1L);

        assertThrows(TaskException.class, () -> taskRepository.findById(1L).orElseThrow(TaskException::new));
    }

    @Test
    @DisplayName(value = "test save task success")
    @Transactional
    void save() {
        TaskDto taskDto = new TaskDto();
        taskDto.setUserId(2L);
        taskDto.setProjectId(1L);
        taskDto.setStartDate(LocalDate.now());
        taskDto.setTitle("some title");
        taskDto.setStatus("INACTIVE");

        Task task = taskService.save(taskDto);
        assertNotNull(task);
        assertNotNull(task.getId());
        assertNotNull(task.getExecutor());
        assertNotNull(task.getProject());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getStartDate(), task.getStartDate());
    }
}