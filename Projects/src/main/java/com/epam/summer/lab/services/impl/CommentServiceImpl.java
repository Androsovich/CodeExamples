package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.dto.CommentDto;
import com.epam.summer.lab.utils.mapper.CommentMapper;
import com.epam.summer.lab.entities.Comment;
import com.epam.summer.lab.repositories.CommentRepository;
import com.epam.summer.lab.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper mapper;

    @Autowired
    CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment save(CommentDto commentDto) {
        Comment comment = mapper.commentDtoToComment(commentDto);
        return commentRepository.save(comment);
    }
}