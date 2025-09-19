package com.lbraz.lms.service;

import com.lbraz.lms.dto.CourseDTO;
import com.lbraz.lms.entity.Course;
import java.util.UUID;

public interface CourseService extends BaseService<Course, UUID> {
    Course createCourse(CourseDTO courseDTO);
    Course updateCourse(UUID id, CourseDTO courseDTO);
}