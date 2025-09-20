package com.lbraz.lms.service.impl;

import com.lbraz.lms.entity.Course;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.ResourceNotFoundException;
import com.lbraz.lms.repository.CourseRepository;
import com.lbraz.lms.service.CourseService;
import com.lbraz.lms.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, UUID> implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional
    public Course save(Course entity) {
        if (repository.findByName(entity.getName()).isPresent()) {
            throw new DuplicateResourceException(MessageUtil.get("error.course.duplicate"));
        }
        return repository.save(entity);
    }

    @Override
    @Transactional
    public Course update(Course entity) {
        String notFoundMessage = MessageUtil.get("error.course.notFound", entity.getId());
        Course existingCourse = repository.findById(entity.getId())
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

        if (!existingCourse.getName().equals(entity.getName())) {
            if (repository.findByName(entity.getName()).isPresent()) {
                throw new DuplicateResourceException(MessageUtil.get("error.course.duplicate"));
            }
        }

        existingCourse.setName(entity.getName());
        existingCourse.setDescription(entity.getDescription());
        existingCourse.setDurationInMonths(entity.getDurationInMonths());

        return repository.save(existingCourse);
    }
}