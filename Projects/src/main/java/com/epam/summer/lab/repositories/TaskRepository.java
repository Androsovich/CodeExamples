package com.epam.summer.lab.repositories;

import com.epam.summer.lab.entities.Task;
import com.epam.summer.lab.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> getTasksByExecutor(User executor);

    Optional<List<Task>> findTaskByProjectId(Long projectId);


}