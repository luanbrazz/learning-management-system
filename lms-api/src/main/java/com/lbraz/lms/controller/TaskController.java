package com.lbraz.lms.controller;

import com.lbraz.lms.entity.Task;
import com.lbraz.lms.service.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@PreAuthorize("hasRole('STUDENT')")
public class TaskController extends BaseController<Task, UUID> {
    public TaskController(TaskService service) {
        super(service);
    }
}