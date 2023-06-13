package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.TaskDto;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class TaskMapperTest {

    @Autowired
    TaskMapper mapper;

    @Autowired
    TaskService taskService;

    @Test
    @Transactional
    void testUpdateTaskFromDtoSuccess() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(3L);
        taskDto.setStatus("COMPLETED");
        Task currentTask = taskService.findTaskById(taskDto.getId());

        assertNotEquals(taskDto.getStatus(), currentTask.getStatus());

        mapper.updateTaskFromDto(taskDto, currentTask);

        assertEquals(currentTask.getStatus(), taskDto.getStatus());
    }

    @Test
    void testTaskDtoToTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setStatus("ACTIVE");
        taskDto.setStartDate(LocalDate.now());
        taskDto.setTitle("Hi");
        taskDto.setProjectId(1L);

        Task task = mapper.taskDtoToTask(taskDto);

        assertEquals(taskDto.getId(),task.getId());
        assertEquals(taskDto.getStatus(),task.getStatus());
        assertEquals(taskDto.getStartDate(),task.getStartDate());
        assertEquals(taskDto.getTitle(),task.getTitle());
        assertEquals(taskDto.getProjectId(),task.getProject().getId());
        assertNull(task.getExecutor());
        assertNull(task.getComments());
        assertNull(task.getEndDate());
    }

    @Test
    void setNestedEntities() {
        String[] commentsId = {"1", "2", "4", "5"};
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setStatus("ACTIVE");
        taskDto.setUserId(1L);
        taskDto.setCommentsId(commentsId);

        Task task = new Task();

        mapper.setNestedEntities(taskDto, task);
        assertNotNull(task.getExecutor());
        assertNotNull(task.getComments());
        assertEquals(4, task.getComments().size());
    }
}