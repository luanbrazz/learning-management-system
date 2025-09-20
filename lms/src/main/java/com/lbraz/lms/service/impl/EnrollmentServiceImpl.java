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

import java.time.LocalDate;
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
        Student student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.get("error.student.notFound", request.studentId())));

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.get("error.course.notFound", request.courseId())));

        long studentEnrollmentsCount = enrollmentRepository.countByStudentId(student.getId());
        if (studentEnrollmentsCount >= MAX_ENROLLMENTS_PER_STUDENT) {
            throw new DuplicateResourceException(MessageUtil.get("error.enrollment.limit"));
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollmentDate(LocalDate.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public Enrollment completeCourse(UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageUtil.get("error.enrollment.notFound", enrollmentId)));

        if (enrollment.getStatus() != CourseStatus.IN_PROGRESS) {
            throw new InvalidStatusChangeException(MessageUtil.get("error.enrollment.invalidStatus", enrollment.getStatus().toString()));
        }

        enrollment.setStatus(CourseStatus.COMPLETED);
        return enrollmentRepository.save(enrollment);
    }

}