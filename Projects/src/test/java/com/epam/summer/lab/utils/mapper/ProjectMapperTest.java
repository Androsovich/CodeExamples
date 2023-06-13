package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.ProjectDto;
import com.epam.summer.lab.entities.Project;
import com.epam.summer.lab.exceptions.ProjectException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WebAppConfiguration
@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class ProjectMapperTest {

    @Autowired
    ProjectMapper mapper;

    @Test
    void testUpdateProjectInvalidStatusExpectedException() {
        ProjectDto projectDto = new ProjectDto();
        Project project = new Project();
        assertThatThrownBy(() -> mapper.updateProject(projectDto, project)).isInstanceOf(ProjectException.class);
    }
}