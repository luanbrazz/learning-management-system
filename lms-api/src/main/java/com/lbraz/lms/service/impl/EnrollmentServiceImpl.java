package com.lbraz.lms.service.impl;

import com.lbraz.lms.dto.EnrollmentRequest;
import com.lbraz.lms.entity.Course;
import com.lbraz.lms.entity.Enrollment;
import com.lbraz.lms.entity.Student;
import com.lbraz.lms.entity.User;
import com.lbraz.lms.enums.CourseStatus;
import com.lbraz.lms.enums.Role;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.EnrollmentExpirationException;
import com.lbraz.lms.exception.InvalidStatusChangeException;
import com.lbraz.lms.exception.ResourceNotFoundException;
import com.lbraz.lms.repository.CourseRepository;
import com.lbraz.lms.repository.EnrollmentRepository;
import com.lbraz.lms.repository.StudentRepository;
import com.lbraz.lms.repository.UserRepository;
import com.lbraz.lms.service.EnrollmentService;
import com.lbraz.lms.util.MessageUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentServiceImpl extends BaseServiceImpl<Enrollment, UUID> implements EnrollmentService {

    private static final int MAX_ENROLLMENTS_PER_STUDENT = 3;
    private static final int MIN_MONTHS_TO_EXPIRE = 6;

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            UserRepository userRepository) {
        super(enrollmentRepository);
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> findEnrollmentsForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameWithStudent(username)
                .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.get("error.user.notFound")));

        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Role.ROLE_ADMIN.getName()));

        if (isAdmin) {
            return enrollmentRepository.findAll();
        } else {
            UUID studentId = user.getStudent() != null ? user.getStudent().getId() : null;
            if (studentId == null) {
                throw new ResourceNotFoundException(MessageUtil.get("error.student.notFound"));
            }
            return enrollmentRepository.findByStudentId(studentId);
        }
    }

    @Override
    @Transactional
    public Enrollment enrollStudent(EnrollmentRequest request) {
        Student student = this.findStudent(request.studentId());
        Course course = this.findCourse(request.courseId());
        enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId())
                .ifPresent(existingEnrollment -> {
                    if (existingEnrollment.getStatus() != CourseStatus.EXPIRED) {
                        throw new DuplicateResourceException(MessageUtil.get("error.enrollment.duplicate"));
                    }
                });

        this.validateEnrollmentLimit(student.getId());

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollmentDate(LocalDateTime.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public Enrollment completeCourse(UUID enrollmentId) {
        Enrollment enrollment = this.findEnrollment(enrollmentId);

        this.validateCourseInProgress(enrollment);

        enrollment.setStatus(CourseStatus.COMPLETED);
        enrollment.setCompletionDate(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public void updateExpiredEnrollments(List<UUID> enrollmentIds) {
        for (UUID id : enrollmentIds) {
            Enrollment enrollment = enrollmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.get("error.enrollment.notFound", id)));

            long monthsSinceEnrollment = ChronoUnit.MONTHS.between(enrollment.getEnrollmentDate(), LocalDateTime.now());
            if (monthsSinceEnrollment < MIN_MONTHS_TO_EXPIRE) {
                throw new EnrollmentExpirationException(MessageUtil.get("error.enrollment.notExpired"));
            }

            if (enrollment.getStatus() != CourseStatus.COMPLETED) {
                enrollment.setStatus(CourseStatus.EXPIRED);
                enrollment.setCompletionDate(LocalDateTime.now());
                enrollmentRepository.save(enrollment);
            } else {
                throw new InvalidStatusChangeException(MessageUtil.get("error.enrollment.alreadyCompleted"));
            }
        }
    }

    private Student findStudent(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageUtil.get("error.student.notFound", studentId)));
    }

    private Course findCourse(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageUtil.get("error.course.notFound", courseId)));
    }

    private Enrollment findEnrollment(UUID enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageUtil.get("error.enrollment.notFound", enrollmentId)));
    }

    private void validateEnrollmentLimit(UUID studentId) {
        long enrollments = enrollmentRepository.countByStudentId(studentId);
        if (enrollments >= MAX_ENROLLMENTS_PER_STUDENT) {
            throw new DuplicateResourceException(MessageUtil.get("error.enrollment.limit"));
        }
    }

    private void validateCourseInProgress(Enrollment enrollment) {
        if (enrollment.getStatus() != CourseStatus.IN_PROGRESS) {
            String translatedStatus = MessageUtil.get("courseStatus." + enrollment.getStatus());
            throw new InvalidStatusChangeException(
                    MessageUtil.get("error.enrollment.invalidStatus", translatedStatus));
        }
    }
}