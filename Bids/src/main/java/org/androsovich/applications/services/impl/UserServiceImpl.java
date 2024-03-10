package org.androsovich.applications.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.androsovich.applications.entities.Privilege;
import org.androsovich.applications.entities.Role;
import org.androsovich.applications.entities.User;
import org.androsovich.applications.entities.enums.OperationType;
import org.androsovich.applications.entities.enums.RoleType;
import org.androsovich.applications.exceptions.InvalidRoleException;
import org.androsovich.applications.exceptions.RoleNotFoundException;
import org.androsovich.applications.exceptions.UserNotFoundException;
import org.androsovich.applications.repositories.RoleRepository;
import org.androsovich.applications.repositories.UserRepository;
import org.androsovich.applications.security.IAuthenticationFacade;
import org.androsovich.applications.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.androsovich.applications.constants.Constants.*;
import static org.androsovich.applications.entities.enums.RoleType.ROLE_OPERATOR;
import static org.androsovich.applications.entities.enums.RoleType.ROLE_USER;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Page<User> getAll() {
        return userRepository.findAll(Pageable.unpaged());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ID + id));
    }

    @Override
    @Transactional
    public List<Privilege> getPrivilegesForPrincipal(){
        final Long principalId = authenticationFacade.getPrincipalId();
        User principal = findById(principalId);

        return principal.getRoles().stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    @Transactional
    public void grantRightsUser(Long userId, Role inputRole) {
        final String roleName = inputRole.getName();
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND + roleName));

        User user = findById(userId);
        boolean isRoleUser = checkUserRole(user, ROLE_USER.name());

        if (ROLE_OPERATOR.name().equals(roleName) && isRoleUser) {
            user.getRoles().add(role);
        }else {
            log.error(INVALID_ROLE_MESSAGE +  roleName);
            throw new InvalidRoleException(INVALID_ROLE_MESSAGE);
        }
        log.info("grant rights user : - {} . Role added - {} successfully.",  userId, roleName);
    }

    private boolean checkUserRole(User user, String roleName) {
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(roleName::equals);
    }
}
