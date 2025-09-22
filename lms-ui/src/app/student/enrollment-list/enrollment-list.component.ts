import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EnrollmentService } from '../../services/enrollment.service';
import { Enrollment } from '../../models/enrollment.model';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-enrollment-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './enrollment-list.component.html',
  styleUrls: ['./enrollment-list.component.scss']
})
export class EnrollmentListComponent implements OnInit {
  enrollments: Enrollment[] = [];
  errorMessage: string | null = null;

  constructor(
    private enrollmentService: EnrollmentService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadEnrollments();
  }

  loadEnrollments(): void {
    this.enrollmentService.findAll().subscribe({
      next: (data) => {
        this.enrollments = data;
      },
      error: (err) => {
        this.notificationService.showError(err.error?.message);
      }
    });
  }

  completeCourse(enrollmentId: string): void {
    if (confirm('Tem certeza que deseja marcar este curso como concluído?')) {
      this.enrollmentService.completeCourse(enrollmentId).subscribe({
        next: (updatedEnrollment) => {
          this.notificationService.showSuccess('Curso concluído com sucesso!');
          this.loadEnrollments();
        },
        error: (err) => {
          this.notificationService.showError(err.error?.message || 'Não foi possível concluir o curso.');
        }
      });
    }
  }
}
