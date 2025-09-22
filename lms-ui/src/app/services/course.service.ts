import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseService } from './base.service';
import { Course } from '../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService extends BaseService<Course, string> {
  constructor(http: HttpClient) {
    super(http, 'courses');
  }
}
