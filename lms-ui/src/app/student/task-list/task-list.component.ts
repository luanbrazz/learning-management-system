import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../services/task.service';
import { Task } from '../../models/task.model';
import { NotificationService } from '../../services/notification.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];
  enrollmentId: string | null = null;
  errorMessage: string | null = null;
  isAdmin: boolean = false;

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute,
    private notificationService: NotificationService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.enrollmentId = this.route.snapshot.paramMap.get('enrollmentId');
    this.isAdmin = this.authService.isAdmin();
    if (this.enrollmentId) {
      this.loadTasks(this.enrollmentId);
    }
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

  deleteTask(taskId: string): void {
    if (this.isAdmin) {
      this.notificationService.showError('Administradores não podem excluir tarefas de estudantes.');
      return;
    }
    const task = this.tasks.find(t => t.id === taskId);
    if (task && task.enrollment.status === 'COMPLETED') {
      this.notificationService.showError('Não é possível excluir tarefas de uma matrícula concluída.');
      return;
    }
    if (confirm('Tem certeza que deseja excluir esta tarefa?')) {
      this.taskService.delete(taskId).subscribe({
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
  }
}
