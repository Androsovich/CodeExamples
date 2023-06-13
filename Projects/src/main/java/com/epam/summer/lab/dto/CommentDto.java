package com.epam.summer.lab.dto;

import com.epam.summer.lab.dto.transfer.New;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
public class CommentDto {
    @Null
    private Long id;

    @NotNull(groups = {New.class})
    private String content;

    @NotNull(groups = {New.class})
    private Long taskId;
}