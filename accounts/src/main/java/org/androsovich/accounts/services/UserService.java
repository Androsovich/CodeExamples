package org.androsovich.accounts.services;

import org.androsovich.accounts.dto.user.UserRequest;
import org.androsovich.accounts.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface UserService {

    Page<User> findAllWithFilteringAndSorting(String filterFirstName, String filterLastName, Pageable pageable);

    User save(User user);

    Page<User> findAllByBirthdayGreaterThanEqual(LocalDate birthDay, Pageable pageable);

    User findById(Long id);

    User findByPhone(String phone);

    User findByEmail(String email);

    User update(UserRequest userRequest);
}
