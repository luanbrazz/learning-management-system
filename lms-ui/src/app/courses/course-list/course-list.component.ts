import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CourseService} from '../../services/course.service';
import {Course} from '../../models/course.model';
import {RouterLink} from '@angular/router';
import {NotificationService} from '../../services/notification.service';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {
  courses: Course[] = [];

  constructor(private courseService: CourseService,
              private notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {
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

  deleteCourse(id: string): void {
    if (confirm('Tem certeza que deseja excluir este curso?')) {
      this.courseService.delete(id).subscribe({
        next: () => {
          this.notificationService.showSuccess('Curso excluído com sucesso!');
          this.loadCourses();
        },
        error: (err) => {
          this.notificationService.showError(err.error?.message);
        }
      });
    }
  }
}
