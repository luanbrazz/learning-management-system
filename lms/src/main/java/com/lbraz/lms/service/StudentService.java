package com.lbraz.lms.service;

import com.lbraz.lms.dto.StudentRegistrationDTO;
import com.lbraz.lms.entity.Student;

import java.util.UUID;

public interface StudentService extends BaseService<Student, UUID> {
    void registerStudent(StudentRegistrationDTO studentDTO);
}