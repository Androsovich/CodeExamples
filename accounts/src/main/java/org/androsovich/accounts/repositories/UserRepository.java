package org.androsovich.accounts.repositories;

import org.androsovich.accounts.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

import static org.androsovich.accounts.constants.Queries.FILTER_USERS_ON_FIRST_NAME_AND_LAST_NAME_QUERY;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    User findByEmailOrPhone(String email, String phone);

    @Query(value = FILTER_USERS_ON_FIRST_NAME_AND_LAST_NAME_QUERY)
    Page<User> findByFirstNameLikeAndLastNameLike(String filterFirstName, String filterLastName, Pageable pageable);

    Page<User> findAllByBirthdayGreaterThanEqual(LocalDate birthDay, Pageable pageable);
}
