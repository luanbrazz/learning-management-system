package com.lbraz.lms.controller;

import com.lbraz.lms.dto.StudentRegistrationRequest;
import com.lbraz.lms.entity.Student;
import com.lbraz.lms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController extends BaseController<Student, UUID> {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        super(studentService);
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@Valid @RequestBody StudentRegistrationRequest studentRegistrationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.register(studentRegistrationRequest));
    }
}