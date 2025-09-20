package com.lbraz.lms.service.impl;

import com.lbraz.lms.entity.TaskCategory;
import com.lbraz.lms.repository.TaskCategoryRepository;
import com.lbraz.lms.service.TaskCategoryService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class TaskCategoryServiceImpl extends BaseServiceImpl<TaskCategory, UUID> implements TaskCategoryService {
    public TaskCategoryServiceImpl(TaskCategoryRepository repository) {
        super(repository);
    }
}