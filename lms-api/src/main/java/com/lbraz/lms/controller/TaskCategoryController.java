package com.lbraz.lms.controller;

import com.lbraz.lms.entity.TaskCategory;
import com.lbraz.lms.service.TaskCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/task-categories")
@PreAuthorize("hasRole('STUDENT')")
public class TaskCategoryController extends BaseController<TaskCategory, UUID> {
    public TaskCategoryController(TaskCategoryService service) {
        super(service);
    }
}