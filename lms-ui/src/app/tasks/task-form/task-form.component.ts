import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task.model';
import {TaskCategory} from '../../models/task-category.model';
import {TaskCategoryService} from '../../services/task-category.service';
import {NotificationService} from '../../services/notification.service';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss']
})
export class TaskFormComponent implements OnInit {
  taskForm: FormGroup;
  isEditMode = false;
  taskId: string | null = null;
  enrollmentId: string | null = null;
  taskCategories: TaskCategory[] = [];

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private taskCategoryService: TaskCategoryService,
    private notificationService: NotificationService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.taskForm = this.fb.group({
      enrollment: this.fb.group({
        id: ['', Validators.required]
      }),
      taskCategory: ['', Validators.required],
      description: ['', Validators.required],
      logDate: ['', Validators.required],
      timeSpentInMinutes: ['', [Validators.required, Validators.min(30)]]
    });
  }

  ngOnInit(): void {
    this.enrollmentId = this.route.snapshot.paramMap.get('enrollmentId');
    this.taskForm.get('enrollment.id')?.setValue(this.enrollmentId);
    this.loadTaskCategories();
    this.taskId = this.route.snapshot.paramMap.get('taskId');
    if (this.taskId) {
      this.isEditMode = true;
      this.taskService.findById(this.taskId).subscribe({
        next: (task) => {
          this.taskForm.patchValue({
            description: task.description,
            logDate: task.logDate,
            timeSpentInMinutes: task.timeSpentInMinutes,
            taskCategory: task.taskCategory.id // Definir apenas o ID da categoria
          });
        },
        error: (err) => {
          this.notificationService.showError(err.error?.message || 'Erro ao carregar tarefa.');
        }
      });
    }
  }

  loadTaskCategories(): void {
    this.taskCategoryService.findAll().subscribe({
      next: (categories) => {
        this.taskCategories = categories;
      },
      error: (err) => {
        this.notificationService.showError(err.error?.message || 'Erro ao carregar categorias.');
      }
    });
  }

  onSubmit(): void {
    if (this.taskForm.valid) {
      const taskData: Task = {
        ...this.taskForm.value,
        taskCategory: {id: this.taskForm.value.taskCategory} // Estrutura esperada pelo backend
      };
      if (this.isEditMode && this.taskId) {
        this.taskService.update(this.taskId, taskData).subscribe({
          next: () => {
            this.notificationService.showSuccess('Tarefa atualizada com sucesso!');
            this.router.navigate(['/student/enrollments', this.enrollmentId, 'tasks']);
          },
          error: (err) => {
            this.notificationService.showError(err.error?.message || 'Erro ao atualizar tarefa.');
          }
        });
      } else {
        this.taskService.save(taskData).subscribe({
          next: () => {
            this.notificationService.showSuccess('Tarefa criada com sucesso!');
            this.router.navigate(['/student/enrollments', this.enrollmentId, 'tasks']);
          },
          error: (err) => {
            this.notificationService.showError(err.error?.message || 'Erro ao criar tarefa.');
          }
        });
      }
    }
  }
}
