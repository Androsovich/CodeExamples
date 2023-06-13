package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.CommentDto;
import com.epam.summer.lab.entities.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class CommentMapperTest {
    @Autowired
   CommentMapper commentMapper;

    @Test
    void testConvertCommentDtoToComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setTaskId(1L);

        Comment comment = commentMapper.commentDtoToComment(commentDto);
        assertEquals(1, comment.getTask().getId());
    }
}