package org.androsovich.accounts.repositories;

import org.androsovich.accounts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByEmailOrPhone(String email, String phone);
}
