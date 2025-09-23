package com.lbraz.lms.controller;

import com.lbraz.lms.entity.Course;
import com.lbraz.lms.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CourseController extends BaseController<Course, UUID> {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable UUID id, @RequestBody Course entity) {
        return ResponseEntity.ok(courseService.update(entity, id));
    }
}