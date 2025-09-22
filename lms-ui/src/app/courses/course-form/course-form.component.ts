import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {CommonModule} from '@angular/common';
import {CourseService} from '../../services/course.service';
import {Course} from '../../models/course.model';
import {NotificationService} from '../../services/notification.service';

@Component({
  selector: 'app-course-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {
  courseForm: FormGroup;
  isEditMode = false;
  courseId: string | null = null;

  constructor(
    private fb: FormBuilder,
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute,
    private notificationService: NotificationService
  ) {
    this.courseForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', Validators.required],
      durationInMonths: ['', [Validators.required, Validators.min(1), Validators.max(6)]]
    });
  }

  ngOnInit(): void {
    this.courseId = this.route.snapshot.paramMap.get('id');
    if (this.courseId) {
      this.isEditMode = true;
      this.courseService.findById(this.courseId).subscribe({
        next: (course) => {
          this.courseForm.patchValue(course);
        },
        error: (err) => {
          this.notificationService.showError(err.error?.message);
          this.router.navigate(['/courses']);
        }
      });
    }
  }

  onSubmit(): void {
    if (this.courseForm.valid) {
      const courseData: Course = this.courseForm.value;
      if (this.isEditMode && this.courseId) {
        this.courseService.update(this.courseId, courseData).subscribe({
          next: () => {
            this.notificationService.showSuccess('Curso atualizado com sucesso!');
            this.router.navigate(['/courses']);
          },
          error: (err) => {
            this.notificationService.showError(err.error?.message || 'Erro ao atualizar o curso.');
          }
        });
      } else {
        this.courseService.save(courseData).subscribe({
          next: () => {
            this.notificationService.showSuccess('Curso criado com sucesso!');
            this.router.navigate(['/courses']);
          },
          error: (err) => {
            this.notificationService.showError(err.error?.message || 'Erro ao criar o curso.');
          }
        });
      }
    }
  }
}
