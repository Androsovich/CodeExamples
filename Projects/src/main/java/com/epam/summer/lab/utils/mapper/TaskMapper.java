package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.dto.TaskDto;
import com.epam.summer.lab.entities.Comment;
import com.epam.summer.lab.entities.Project;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.exceptions.TaskException;
import com.epam.summer.lab.utils.validators.StatusValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.epam.summer.lab.constants.Constants.*;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class TaskMapper {
    public static CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Autowired
    protected StatusValidator statusValidator;

    public abstract TaskDto toTaskDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateTaskFromDto(TaskDto taskDto, @MappingTarget Task entity);

    public abstract List<TaskDto> toTaskDto(Collection<Task> tasks);

    public Task taskDtoToTask(TaskDto taskDto) {
        if (!statusValidator.checkStatus(taskDto.getStatus())) {
            throw new TaskException(STATUS_NOT_VALID + taskDto.getStatus());
        }

        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setStatus(taskDto.getStatus());
        task.setStartDate(taskDto.getStartDate());
        task.setEndDate(taskDto.getEndDate());
        setNestedEntities(taskDto, task);

        return task;
    }

    public void setNestedEntities(TaskDto taskDto, Task task) {
        List<Comment> comments = Objects.nonNull(taskDto.getCommentsId()) ? convertIds(taskDto.getCommentsId()) : task.getComments();
        Project project = Objects.nonNull(taskDto.getProjectId()) ? getProject(taskDto) : task.getProject();
        User executor = Objects.nonNull(taskDto.getUserId()) ? getExecutor(taskDto) : task.getExecutor();

        task.setComments(comments);
        task.setExecutor(executor);
        task.setProject(project);
    }

    private Project getProject(TaskDto taskDto) {
        Project project = new Project();
        project.setId(taskDto.getProjectId());
        return project;
    }

    private User getExecutor(TaskDto taskDto) {
        User executor = new User();
        executor.setId(taskDto.getUserId());
        return executor;
    }

    private List<Comment> convertIds(String[] commentsId) {
        List<Comment> comments;
        try {
            comments = Arrays.stream(commentsId)
                    .map(id -> {
                        Comment comment = new Comment();
                        comment.setId(Long.valueOf(id));
                        return comment;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(EXCEPTION_CONVERT_TO_COMMENT, e);
            throw new TaskException(EXCEPTION_CONVERT_TO_COMMENT, e);
        }
        return comments;
    }
}