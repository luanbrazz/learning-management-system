import {Component} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {AuthService} from '../../services/auth.service';
import {LoginRequest} from '../../models/login-request.model';
import {tap} from 'rxjs';
import {NotificationService} from '../../services/notification.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const credentials: LoginRequest = this.loginForm.value;
      this.authService.login(credentials).pipe(
        tap((response) => {
          if (response.role === 'ROLE_ADMIN') {
            this.router.navigate(['/admin-dashboard']);
          } else {
            this.router.navigate(['/student-dashboard']);
          }
        })
      ).subscribe({
        next: (response) => {
          this.notificationService.showSuccess("Bem-vindo(a) de volta, " + this.authService.getFullName() + "!" );
        },
        error: (err) => {
          this.notificationService.showError(err.error?.message || 'Erro ao fazer login. Verifique suas credenciais e tente novamente.');
        }
      });
    }
  }
}
