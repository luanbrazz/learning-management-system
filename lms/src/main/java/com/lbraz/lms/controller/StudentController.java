package com.lbraz.lms.controller;

import com.lbraz.lms.entity.Student;
import com.lbraz.lms.service.StudentService;
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
}