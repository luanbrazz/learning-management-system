import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CourseService} from '../../services/course.service';
import {EnrollmentService} from '../../services/enrollment.service';
import {Course} from '../../models/course.model';
import {NotificationService} from '../../services/notification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-course-browser',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './course-browser.component.html',
  styleUrls: ['./course-browser.component.scss']
})
export class CourseBrowserComponent implements OnInit {
  courses: Course[] = [];
  studentId: string | null = null;

  constructor(
    private courseService: CourseService,
    private enrollmentService: EnrollmentService,
    private notificationService: NotificationService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.studentId = localStorage.getItem('student_id');
    this.loadCourses();
  }

  loadCourses(): void {
    this.courseService.findAll().subscribe({
      next: (courses) => {
        this.courses = courses;
      },
      error: (err) => {
        this.notificationService.showError(err.error?.message);
      }
    });
  }

  enrollInCourse(courseId: string): void {
    if (!this.studentId) {
      this.notificationService.showWarning('Faça login novamente.')
      this.router.navigate(['/auth/login']);
      return;
    }

    const enrollmentRequest = {studentId: this.studentId, courseId};
    this.enrollmentService.enrollStudent(enrollmentRequest).subscribe({
      next: (enrollment) => {
        this.notificationService.showSuccess('Matrícula realizada com sucesso. Código da matrícula: ' + enrollment.id);
        this.loadCourses();
      },
      error: (err) => {
        this.notificationService.showError(err.error?.message);
      }
    });
  }
}
