package com.lbraz.lms.controller;

import com.lbraz.lms.dto.EnrollmentRequest;
import com.lbraz.lms.entity.Enrollment;
import com.lbraz.lms.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController extends BaseController<Enrollment, UUID> {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        super(enrollmentService);
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enrollStudent(@Valid @RequestBody EnrollmentRequest enrollmentDTO) {
        Enrollment newEnrollment = enrollmentService.enrollStudent(enrollmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEnrollment);
    }
}
