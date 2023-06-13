package com.epam.summer.lab.dto;

import com.epam.summer.lab.dto.transfer.Delete;
import com.epam.summer.lab.dto.transfer.New;
import com.epam.summer.lab.dto.transfer.Update;
import com.epam.summer.lab.dto.transfer.UpdateStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
public class ProjectDto {
    @Null(groups = New.class)
    @NotNull(groups = {UpdateStatus.class, Update.class, Delete.class})
    private Long id;

    @NotNull(groups = {New.class})
    private String title;

    @NotNull(groups = {New.class, UpdateStatus.class})
    private String status;

    @Null(groups = {New.class})
    private UserDto user;

    @Null(groups = {New.class})
    private List<TaskDto> tasks;

    @Null(groups = {New.class})
    private List<UserDto> projectUsers;
}
