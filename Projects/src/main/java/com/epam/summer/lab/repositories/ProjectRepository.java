package com.epam.summer.lab.repositories;

import com.epam.summer.lab.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.summer.lab.constants.SqlQueries.SQL_SAVE_USER_TO_PROJECT;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAll();

    @Modifying
    @Query(value = SQL_SAVE_USER_TO_PROJECT, nativeQuery = true)
    void saveUserToProject(@Param("project_id") Long projectId, @Param("user_id") Long userId);

    List<Project> findAllByProjectUsersId(Long id);
}