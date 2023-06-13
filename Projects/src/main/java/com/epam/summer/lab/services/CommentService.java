package com.epam.summer.lab.services;

import com.epam.summer.lab.dto.CommentDto;
import com.epam.summer.lab.entities.Comment;

public interface CommentService {
    Comment save(CommentDto commentDto);
}