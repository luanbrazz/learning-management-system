import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task.model';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-task-view',
  imports: [
    RouterLink, CommonModule
  ],
  template: `
    <div class="container mt-4">
      <h2>Detalhes da Tarefa</h2>
      <div *ngIf="task">
        <p><strong>Descrição:</strong> {{ task.description }}</p>
        <p><strong>Categoria:</strong> {{ task.taskCategory.name }}</p>
        <p><strong>Data:</strong> {{ task.logDate }}</p>
        <p><strong>Tempo Gasto (min):</strong> {{ task.timeSpentInMinutes }}</p>
        <a [routerLink]="['/student/enrollments', enrollmentId, 'tasks']" class="btn btn-primary">Voltar</a>
      </div>
      <div *ngIf="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>
    </div>
  `
})
export class TaskViewComponent implements OnInit {
  task: Task | null = null;
  enrollmentId: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.enrollmentId = this.route.snapshot.paramMap.get('enrollmentId');
    const taskId = this.route.snapshot.paramMap.get('taskId');
    if (taskId) {
      this.taskService.findById(taskId).subscribe({
        next: (task) => this.task = task,
        error: (err) => {
          this.errorMessage = 'Não foi possível carregar os detalhes da tarefa.';
        }
      });
    }
  }
}
