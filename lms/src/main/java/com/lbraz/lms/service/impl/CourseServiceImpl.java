package com.lbraz.lms.service.impl;

import com.lbraz.lms.dto.CourseDTO;
import com.lbraz.lms.entity.Course;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.ResourceNotFoundException;
import com.lbraz.lms.repository.CourseRepository;
import com.lbraz.lms.service.CourseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, UUID> implements CourseService {

    private final CourseRepository repository;
    private final MessageSource messageSource;

    public CourseServiceImpl(CourseRepository repository, MessageSource messageSource) {
        super(repository);
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public Course createCourse(CourseDTO courseDTO) {
        if (repository.findByName(courseDTO.name()).isPresent()) {
            throw new DuplicateResourceException(messageSource.getMessage("error.course.duplicate", null, null));
        }

        Course course = Course.builder()
                .name(courseDTO.name())
                .description(courseDTO.description())
                .durationInMonths(courseDTO.durationInMonths())
                .build();

        return repository.save(course);
    }

    @Override
    @Transactional
    public Course updateCourse(UUID id, CourseDTO courseDTO) {
        String notFoundMessage = messageSource.getMessage("error.course.notFound", new Object[]{id}, null);
        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(notFoundMessage));

        if (!existingCourse.getName().equals(courseDTO.name())) {
            if (repository.findByName(courseDTO.name()).isPresent()) {
                throw new DuplicateResourceException(messageSource.getMessage("error.course.duplicate", null, null));
            }
        }

        existingCourse.setName(courseDTO.name());
        existingCourse.setDescription(courseDTO.description());
        existingCourse.setDurationInMonths(courseDTO.durationInMonths());

        return repository.save(existingCourse);
    }
}