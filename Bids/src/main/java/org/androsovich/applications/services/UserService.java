package org.androsovich.applications.services;

import org.androsovich.applications.entities.Privilege;
import org.androsovich.applications.entities.Role;
import org.androsovich.applications.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<User> getAll(Pageable pageable);

    Page<User> getAll();

    User findById(Long id);

    List<Privilege> getPrivilegesForPrincipal();

    void grantRightsUser(Long userId, Role role);
}
