package com.lbraz.lms.service.impl;

import com.lbraz.lms.entity.Task;
import com.lbraz.lms.repository.TaskRepository;
import com.lbraz.lms.service.TaskService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, UUID> implements TaskService {
    public TaskServiceImpl(TaskRepository repository) {
        super(repository);
    }
}