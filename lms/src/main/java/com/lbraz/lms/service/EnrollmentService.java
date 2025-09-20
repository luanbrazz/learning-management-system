package com.lbraz.lms.service;

import com.lbraz.lms.dto.EnrollmentRequest;
import com.lbraz.lms.entity.Enrollment;
import java.util.UUID;

public interface EnrollmentService extends BaseService<Enrollment, UUID> {
    Enrollment enrollStudent(EnrollmentRequest enrollmentDTO);

    Enrollment completeCourse(UUID enrollmentId);

}