package com.epam.summer.lab.security;

import com.epam.summer.lab.config.MvcConfig;
import com.epam.summer.lab.config.OrmConfig;
import com.epam.summer.lab.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitWebConfig({MvcConfig.class, RootConfig.class, OrmConfig.class})
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    @DisplayName(value = "loadUserByUsername success")
    void testLoadUserByUsername() {
        final String name = "werty";

        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        assertThat(name).isEqualTo(userDetails.getUsername());
        assertThat(userDetails.getAuthorities().isEmpty()).isFalse();
        assertThat(Objects.nonNull(userDetails.getPassword())).isTrue();
    }

    @ParameterizedTest
    @DisplayName(value = "loadUserByUsername wrong name expected Exception")
    @ValueSource(strings = {"ddssa", "dasdsad", "dasdad"})
    @Transactional
    void testLoadUserByUsernameExpectedException(String name) {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(name)).isInstanceOf(UsernameNotFoundException.class);
    }
}