package com.lbraz.lms.service.impl;

import com.lbraz.lms.dto.EnrollmentRequest;
import com.lbraz.lms.entity.Course;
import com.lbraz.lms.entity.Enrollment;
import com.lbraz.lms.entity.Student;
import com.lbraz.lms.enums.CourseStatus;
import com.lbraz.lms.exception.DuplicateResourceException;
import com.lbraz.lms.exception.InvalidStatusChangeException;
import com.lbraz.lms.exception.ResourceNotFoundException;
import com.lbraz.lms.repository.CourseRepository;
import com.lbraz.lms.repository.EnrollmentRepository;
import com.lbraz.lms.repository.StudentRepository;
import com.lbraz.lms.service.EnrollmentService;
import com.lbraz.lms.util.MessageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentServiceImpl extends BaseServiceImpl<Enrollment, UUID> implements EnrollmentService {

    private static final int MAX_ENROLLMENTS_PER_STUDENT = 3;

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        super(enrollmentRepository);
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Enrollment enrollStudent(EnrollmentRequest request) {
        Student student = this.findStudent(request.studentId());
        Course course = this.findCourse(request.courseId());

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

            if (enrollment.getStatus() != CourseStatus.COMPLETED) {
                enrollment.setStatus(CourseStatus.EXPIRED);
                enrollment.setCompletionDate(LocalDateTime.now());
                enrollmentRepository.save(enrollment);
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