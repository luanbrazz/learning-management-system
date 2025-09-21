package com.lbraz.lms.controller;

import com.lbraz.lms.dto.EnrollmentRequest;
import com.lbraz.lms.entity.Enrollment;
import com.lbraz.lms.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollments")
@PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
public class EnrollmentController extends BaseController<Enrollment, UUID> {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        super(enrollmentService);
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<Enrollment> enrollStudent(@Valid @RequestBody EnrollmentRequest enrollmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enrollStudent(enrollmentDTO));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Enrollment> completeCourse(@PathVariable UUID id) {
        Enrollment completedEnrollment = enrollmentService.completeCourse(id);
        return ResponseEntity.ok(completedEnrollment);
    }

    @PatchMapping("/expire")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateExpiredEnrollments(@RequestBody List<UUID> enrollmentIds) {
        enrollmentService.updateExpiredEnrollments(enrollmentIds);
        return ResponseEntity.noContent().build();
    }
}
