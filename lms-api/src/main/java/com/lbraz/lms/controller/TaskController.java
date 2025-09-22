package com.lbraz.lms.controller;

import com.lbraz.lms.entity.Task;
import com.lbraz.lms.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_ADMIN')")
public class TaskController extends BaseController<Task, UUID> {

    private final TaskService taskService;

    public TaskController(TaskService service) {
        super(service);
        this.taskService = service;
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<Task>> findByEnrollmentId(@PathVariable UUID enrollmentId) {
        List<Task> tasks = taskService.findByEnrollmentId(enrollmentId);
        return ResponseEntity.ok(tasks);
    }
}