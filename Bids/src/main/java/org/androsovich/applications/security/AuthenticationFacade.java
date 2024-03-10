package org.androsovich.applications.security;

import org.androsovich.applications.entities.User;
import org.androsovich.applications.exceptions.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.androsovich.applications.constants.Constants.AUTH_USER_NOT_FOUND_ID;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Long getPrincipalId() {
        return Optional.ofNullable(getAuthentication().getPrincipal())
                .map(o -> (User) o)
                .map(User::getId)
                .orElseThrow(() -> new UserNotFoundException(AUTH_USER_NOT_FOUND_ID));
    }
}
