package com.lbraz.lms.service.impl;

import com.lbraz.lms.entity.Course;
import com.lbraz.lms.entity.User;
import com.lbraz.lms.enums.Role;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.ResourceNotFoundException;
import com.lbraz.lms.repository.CourseRepository;
import com.lbraz.lms.repository.EnrollmentRepository;
import com.lbraz.lms.repository.UserRepository; // Importe o UserRepository
import com.lbraz.lms.service.CourseService;
import com.lbraz.lms.util.MessageUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, UUID> implements CourseService {

    private final CourseRepository repository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository; // Adicione o UserRepository

    public CourseServiceImpl(CourseRepository repository, EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        super(repository);
        this.repository = repository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
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
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsernameWithStudent(username)
                .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.get("error.user.notFound", username)));

        if (user.getRole() == Role.ROLE_ADMIN) {
            return repository.findAll();
        } else {
            List<Course> courses = repository.findAll();

            // Lógica para estudantes: verifica se já está matriculado
            return courses.stream().map(course -> {
                boolean isEnrolled = enrollmentRepository.findByStudentIdAndCourseId(user.getStudent().getId(), course.getId()).isPresent();
                course.setEnrolled(isEnrolled);
                return course;
            }).collect(Collectors.toList());
        }
    }
}