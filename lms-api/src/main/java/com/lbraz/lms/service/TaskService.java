package com.lbraz.lms.service;

import com.lbraz.lms.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService extends BaseService<Task, UUID> {
    List<Task> findByEnrollmentId(UUID enrollmentId);
}