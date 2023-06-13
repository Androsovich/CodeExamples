package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.dto.CommentDto;
import com.epam.summer.lab.entities.Comment;
import com.epam.summer.lab.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    public static CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    public abstract CommentDto commentToCommentDto(Comment comment);

    public Comment commentDtoToComment(CommentDto commentDto) {
        Task task = new Task();
        task.setId(commentDto.getTaskId());

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setTask(task);
        return comment;
    }
}