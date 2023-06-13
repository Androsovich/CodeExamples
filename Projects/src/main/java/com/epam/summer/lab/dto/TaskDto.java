package com.epam.summer.lab.dto;

import com.epam.summer.lab.dto.transfer.Delete;
import com.epam.summer.lab.dto.transfer.New;
import com.epam.summer.lab.dto.transfer.Update;
import com.epam.summer.lab.dto.transfer.UpdateStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Getter
@Setter
public class TaskDto {
    @Null(groups = {New.class})
    @NotNull(groups = {UpdateStatus.class, Update.class, Delete.class})
    private Long id;

    @NotNull(groups = {New.class})
    @Null(groups = {UpdateStatus.class})
    private String title;

    @NotNull(groups = {New.class})
    @Null(groups = {UpdateStatus.class})
    private LocalDate startDate;

    @Null(groups = {New.class})
    private LocalDate endDate;

    @NotNull(groups = {New.class, UpdateStatus.class})
    private String status;

    @NotNull(groups = {New.class})
    @Null(groups = {UpdateStatus.class})
    private Long userId;

    @NotNull(groups = {New.class})
    @Null(groups = UpdateStatus.class)
    private Long projectId;

    @Null(groups = {New.class, UpdateStatus.class})
    private String[] commentsId;
}
