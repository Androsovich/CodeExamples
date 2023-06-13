package com.epam.summer.lab.repositories;

import com.epam.summer.lab.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.summer.lab.constants.SqlQueries.SQL_GET_USERS_NOT_IN_PROJECT;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserName(String userName);

    Optional<List<User>> findUsersByProjectsId(Long projectId);

    @Query(value = SQL_GET_USERS_NOT_IN_PROJECT, nativeQuery = true)
    List<User> getUsersNotInProject(@Param("project_id") Long projectId);
}