package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.dto.ProjectDto;
import com.epam.summer.lab.entities.Project;
import com.epam.summer.lab.exceptions.ProjectException;
import com.epam.summer.lab.utils.validators.StatusValidator;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epam.summer.lab.constants.Constants.STATUS_NOT_VALID;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class ProjectMapper {
    public static ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Autowired
    protected StatusValidator statusValidator;

    public abstract ProjectDto projectToProjectDto(Project project);

    public abstract Project projectDtoToProject(ProjectDto projectDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void updateProjectFromDto(ProjectDto projectDto, @MappingTarget Project entity);

    public void updateProject(ProjectDto projectDto, @MappingTarget Project entity) {
        if (!statusValidator.checkStatus(projectDto.getStatus())) {
            throw new ProjectException(STATUS_NOT_VALID + projectDto.getStatus());
        }
        updateProjectFromDto(projectDto, entity);
    }
}