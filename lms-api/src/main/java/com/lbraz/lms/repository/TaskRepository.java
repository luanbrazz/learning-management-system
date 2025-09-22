package com.lbraz.lms.repository;

import com.lbraz.lms.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByEnrollmentId(UUID enrollmentId);
}