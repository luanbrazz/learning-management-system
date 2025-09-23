import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CourseService} from '../../services/course.service';
import {Course} from '../../models/course.model';
import {RouterLink} from '@angular/router';
import {NotificationService} from '../../services/notification.service';
import {ConfirmationModalComponent} from '../../shared/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, RouterLink, ConfirmationModalComponent],
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {
  courses: Course[] = [];
  showConfirmationModal = false;
  courseIdToDelete: string | null = null;

  constructor(
    private courseService: CourseService,
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


  showDeleteConfirmation(id: string): void {
    this.courseIdToDelete = id;
    this.showConfirmationModal = true;
  }

  confirmDeletion(): void {
    if (this.courseIdToDelete) {
      this.courseService.delete(this.courseIdToDelete).subscribe({
        next: () => {
          this.notificationService.showSuccess('Curso excluído com sucesso!');
          this.loadCourses();
        },
        error: (err) => {
          this.notificationService.showError(err.error?.message);
        }
      });
    }
    this.showConfirmationModal = false;
    this.courseIdToDelete = null;
  }

  cancelDeletion(): void {
    this.showConfirmationModal = false;
    this.courseIdToDelete = null;
  }
}
