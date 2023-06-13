package com.epam.summer.lab.controllers;

import com.epam.summer.lab.dto.CommentDto;
import com.epam.summer.lab.dto.transfer.New;
import com.epam.summer.lab.entities.Comment;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@AllArgsConstructor
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(value = "/api/comment/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<User> save(@Validated(New.class) @RequestBody CommentDto commentDto) {
        Comment resultComment = commentService.save(commentDto);
        return Objects.nonNull(resultComment) ? new ResponseEntity<>(OK) : new ResponseEntity<>(BAD_REQUEST);
    }
}
