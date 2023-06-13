package com.epam.summer.lab.utils.mapper;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void testConvertLongArrayToRolesInUserDtoToUser() {
        String[] roles = {"1", "2"};
        UserDto userDto = new UserDto();
        userDto.setRole(roles);

        User user = userMapper.userDtoToUser(userDto);
        assertEquals(2, user.getRoles().size());
        assertEquals(1, user.getRoles().get(0).getId());
        assertEquals(2, user.getRoles().get(1).getId());
    }
}