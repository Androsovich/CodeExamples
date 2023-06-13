package com.epam.summer.lab.services.impl;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import com.epam.summer.lab.dto.UserDto;
import com.epam.summer.lab.entities.Role;
import com.epam.summer.lab.entities.User;
import com.epam.summer.lab.exceptions.UserException;
import com.epam.summer.lab.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("test getAuthenticationUser Success")
    @WithMockUser(username = "werty", roles = "ADMIN")
    @Transactional
    void checkAuthenticationUser() {
        final String username = "werty";
        final String roleName = "ROLE_ADMIN";

        User user = userService.getAuthenticationUser();
        List<String> roles =
                user
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList());

        assertThat(user.getUserName()).isEqualTo(username);
        assertThat(roles.contains(roleName)).isTrue();
    }


    @Test
    @DisplayName("test getAuthenticationUser is Null expected UserException")
    @Transactional
    void getAuthenticationUserIsNullExpectException() {
        assertThatThrownBy(() -> userService.getAuthenticationUser()).isInstanceOf(UserException.class);
    }


    @Test
    @DisplayName("test save UserDto success")
    @Transactional
    void saveUserSuccess() {
        final String PASSWORD = "test password";
        final String[] roles = {"1"};

        UserDto userDto = new UserDto();
        userDto.setPassword(PASSWORD);
        userDto.setRole(roles);
        User user = userService.save(userDto);

        assertTrue(passwordEncoder.matches(PASSWORD, user.getPassword()));
        assertNotNull(user.getId());
    }

    @Test
    @DisplayName("test getUsersByProjectId success")
    @Transactional
    void testUsersByProjectId() {
        final Long projectId = 1L;
        List<UserDto> users = userService.getUsersByProjectId(projectId);
        assertNotNull(users);
        assertThat(users.size() > 0).isTrue();
    }

    @ParameterizedTest
    @DisplayName(value = "test getAuthenticationUser is wrong id expected UserException")
    @ValueSource(ints = {-1, 0, 158, -3, Integer.MAX_VALUE})
    @Transactional
    void getUsersByProjectIdIsWrongIdExpectException(long projectId) {
        assertThatThrownBy(() -> userService.getUsersByProjectId(projectId)).isInstanceOf(UserException.class);
    }

    @ParameterizedTest
    @DisplayName(value = "test getUsersNotInProject success")
    @ValueSource(ints = {1, 2, 3})
    @Transactional
    void getUsersNotInProjectSuccess(long projectId) {
        List<UserDto> userDtoList =  userService.getUsersNotInProject(projectId);
        assertThat(userDtoList.isEmpty()).isFalse();
        assertThat(userDtoList.size()).isEqualTo(1);

    }

    @Test
    @DisplayName(value = "test findAl() success")
    void testFindAll() {
        List<User> users = userService.findAll();
        assertThat(users.isEmpty()).isFalse();
        assertThat(users.size()).isEqualTo(4);
    }
}