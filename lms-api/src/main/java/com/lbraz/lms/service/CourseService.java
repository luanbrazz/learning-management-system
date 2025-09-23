package com.lbraz.lms.service;

import com.lbraz.lms.entity.Course;

import java.util.UUID;

public interface CourseService extends BaseService<Course, UUID> {
    Course update(Course entity, UUID id);
}