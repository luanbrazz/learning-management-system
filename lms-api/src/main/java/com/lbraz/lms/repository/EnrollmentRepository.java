package com.lbraz.lms.repository;

import com.lbraz.lms.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    long countByStudentId(UUID studentId);

    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
}