package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.CommentDto;
import com.epam.summer.lab.entities.Comment;
import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.services.CommentService;
import com.epam.summer.lab.services.ProjectService;
import com.epam.summer.lab.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    @Test
    @Transactional
    public void saveCommentSuccess() {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(" test comment");
        commentDto.setTaskId(2L);

        Comment comment = commentService.save(commentDto);
        Task task = taskService.findTaskById(2L);
        assertEquals(1, task.getComments().size());
        projectService.delete(1L);
    }
}