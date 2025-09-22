package com.lbraz.lms.service.impl;

import com.lbraz.lms.entity.Enrollment;
import com.lbraz.lms.entity.Task;
import com.lbraz.lms.enums.CourseStatus;
import com.lbraz.lms.exception.InvalidTimeException;
import com.lbraz.lms.exception.ResourceNotFoundException;
import com.lbraz.lms.repository.EnrollmentRepository;
import com.lbraz.lms.repository.TaskRepository;
import com.lbraz.lms.service.TaskService;
import com.lbraz.lms.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, UUID> implements TaskService {

    private final EnrollmentRepository enrollmentRepository;
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository, EnrollmentRepository enrollmentRepository) {
        super(repository);
        this.repository = repository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    @Transactional
    public Task save(Task entity) {
        if (entity.getTimeSpentInMinutes() % 30 != 0) {
            throw new InvalidTimeException(MessageUtil.get("error.time.invalid"));
        }

        Enrollment enrollment = enrollmentRepository.findById(entity.getEnrollment().getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.get("error.enrollment.notFound", entity.getEnrollment().getId())));

        entity.setEnrollment(enrollment);

        if (enrollment.getStatus() == CourseStatus.NOT_STARTED) {
            enrollment.setStatus(CourseStatus.IN_PROGRESS);
            enrollmentRepository.save(enrollment);
        }

        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByEnrollmentId(UUID enrollmentId) {
        return repository.findByEnrollmentId(enrollmentId);
    }
}