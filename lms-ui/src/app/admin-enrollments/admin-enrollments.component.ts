import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EnrollmentService} from '../services/enrollment.service';
import {Enrollment} from '../models/enrollment.model';
import {NotificationService} from '../services/notification.service';
import {RouterLink} from '@angular/router';
import {ConfirmationModalComponent} from '../shared/confirmation-modal/confirmation-modal.component';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-admin-enrollments',
  standalone: true,
  imports: [CommonModule, RouterLink, ConfirmationModalComponent],
  templateUrl: './admin-enrollments.component.html',
  styleUrls: ['./admin-enrollments.component.scss']
})
export class AdminEnrollmentsComponent implements OnInit {
  enrollments: Enrollment[] = [];
  selectedEnrollmentIds: string[] = [];
  showConfirmationModal = false;
  isAdmin: boolean = false;

  constructor(
    private enrollmentService: EnrollmentService,
    private notificationService: NotificationService,
    private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.loadEnrollments();
    this.isAdmin = this.authService.isAdmin();
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

  onSelectEnrollment(id: string): void {
    const index = this.selectedEnrollmentIds.indexOf(id);
    if (index === -1) {
      this.selectedEnrollmentIds.push(id);
    } else {
      this.selectedEnrollmentIds.splice(index, 1);
    }
  }

  expireSelectedEnrollments(): void {
    if (this.selectedEnrollmentIds.length === 0) {
      this.notificationService.showWarning('Selecione pelo menos uma matrícula para expirar.');
      return;
    }
    this.showConfirmationModal = true;
  }

  confirmExpiration(): void {
    this.enrollmentService.updateExpiredEnrollments(this.selectedEnrollmentIds).subscribe({
      next: () => {
        this.notificationService.showSuccess('Matrículas atualizadas com sucesso!');
        this.selectedEnrollmentIds = [];
        this.loadEnrollments();
      },
      error: (err) => {
        this.notificationService.showError(err.error?.message || 'Erro ao atualizar matrículas. Verifique se você tem permissão.');
      }
    });
    this.showConfirmationModal = false;
  }

  cancelExpiration(): void {
    this.showConfirmationModal = false;
  }
}
