package com.lbraz.lms.service;

import com.lbraz.lms.dto.StudentRegistrationRequest;
import com.lbraz.lms.entity.Student;
import jakarta.validation.Valid;

import java.util.UUID;

public interface StudentService extends BaseService<Student, UUID> {
    Student register(@Valid StudentRegistrationRequest studentRegistrationRequest);
}