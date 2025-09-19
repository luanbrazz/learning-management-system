package com.lbraz.lms.controller;

import com.lbraz.lms.dto.CourseDTO;
import com.lbraz.lms.entity.Course;
import com.lbraz.lms.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends BaseController<Course, UUID> {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> create(@Valid @RequestBody CourseDTO courseDTO) {
        Course createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable UUID id, @Valid @RequestBody CourseDTO courseDTO) {
        Course updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }
}