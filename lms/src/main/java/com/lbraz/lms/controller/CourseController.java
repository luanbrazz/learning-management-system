package com.lbraz.lms.controller;

import com.lbraz.lms.entity.Course;
import com.lbraz.lms.service.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends BaseController<Course, UUID> {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }
}