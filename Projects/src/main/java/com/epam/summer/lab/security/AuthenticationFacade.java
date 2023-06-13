package com.epam.summer.lab.security;

import com.epam.summer.lab.exceptions.UserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.epam.summer.lab.constants.Constants.AUTHENTICATION_NOT_FOUND;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new UserException(AUTHENTICATION_NOT_FOUND);
        }
        return authentication;
    }
}