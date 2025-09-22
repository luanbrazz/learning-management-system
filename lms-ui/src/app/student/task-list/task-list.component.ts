import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {CommonModule} from '@angular/common';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task.model';
import {NotificationService} from '../../services/notification.service';
import {AuthService} from '../../services/auth.service';
import {EnrollmentService} from '../../services/enrollment.service';
import {ConfirmationModalComponent} from '../../shared/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, RouterLink, ConfirmationModalComponent],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  enrollmentId: string | null = null;
  errorMessage: string | null = null;
  isAdmin: boolean = false;
  isEnrollmentCompletedOrExpired = false;
  showConfirmationModal = false;
  taskIdToDelete: string | null = null;

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute,
    private notificationService: NotificationService,
    private authService: AuthService,
    private enrollmentService: EnrollmentService
  ) {
  }

  ngOnInit(): void {
    this.enrollmentId = this.route.snapshot.paramMap.get('enrollmentId');
    this.isAdmin = this.authService.isAdmin();

    if (this.enrollmentId) {
      this.loadEnrollmentStatus(this.enrollmentId);
      this.loadTasks(this.enrollmentId);
    } else {
      this.errorMessage = 'ID da matrícula não encontrado.';
      this.notificationService.showError(this.errorMessage);
    }
  }

  loadEnrollmentStatus(enrollmentId: string): void {
    this.enrollmentService.findById(enrollmentId).subscribe({
      next: (enrollment) => {
        this.isEnrollmentCompletedOrExpired = enrollment.status === 'COMPLETED' || enrollment.status === 'EXPIRED';
      },
      error: (err) => {
        this.notificationService.showError('Não foi possível verificar o status da matrícula.');
      }
    });
  }

  loadTasks(enrollmentId: string): void {
    this.taskService.getTasksByEnrollmentId(enrollmentId).subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: (err) => {
        this.errorMessage = 'Não foi possível carregar as tarefas.';
      }
    });
  }

  showDeleteConfirmation(taskId: string): void {
    if (this.isAdmin) {
      this.notificationService.showError('Administradores não podem excluir tarefas de estudantes.');
      return;
    }

    if (this.isEnrollmentCompletedOrExpired) {
      this.notificationService.showError('Não é possível excluir tarefas de um curso concluído ou expirado.');
      return;
    }

    this.taskIdToDelete = taskId;
    this.showConfirmationModal = true;
  }

  confirmDeletion(): void {
    if (this.taskIdToDelete) {
      this.taskService.delete(this.taskIdToDelete).subscribe({
        next: () => {
          this.notificationService.showSuccess('Tarefa excluída com sucesso!');
          if (this.enrollmentId) {
            this.loadTasks(this.enrollmentId);
          }
        },
        error: (err) => {
          this.notificationService.showError(err.error?.message || 'Erro ao excluir a tarefa.');
        }
      });
    }
    this.showConfirmationModal = false;
    this.taskIdToDelete = null;
  }

  cancelDeletion(): void {
    this.showConfirmationModal = false;
    this.taskIdToDelete = null;
  }
}
