import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ViaCepService } from '../../services/via-cep.service';
import { debounceTime, distinctUntilChanged, filter, switchMap } from 'rxjs/operators';
import { StudentRegistrationRequest } from '../../models/student-registration-request.model';
import {NotificationService} from '../../services/notification.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink], // Add RouterLink here
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router,
    private viaCepService: ViaCepService
  ) {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      birthDate: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      zipCode: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      addressLine1: ['', Validators.required],
      addressLine2: [''],
      city: ['', Validators.required],
      state: ['', [Validators.required, Validators.minLength(2)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    this.registerForm.get('zipCode')?.valueChanges.pipe(
      filter((value: string | null) => !!value && value.length === 8),
      debounceTime(500),
      distinctUntilChanged(),
      switchMap(cep => this.viaCepService.find(cep as string))
    ).subscribe({
      next: (address) => {
        this.registerForm.patchValue({
          addressLine1: address.addressLine1 + ", Nº ",
          addressLine2: address.addressLine2,
          city: address.city,
          state: address.state,
        });
      },
      error: (err) => {
        this.notificationService.showError(err.error?.message || 'Erro ao buscar CEP');
      }
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.loading = true;
      const formValue = this.registerForm.value;
      const registrationData: StudentRegistrationRequest = {
        firstName: formValue.firstName,
        lastName: formValue.lastName,
        birthDate: formValue.birthDate,
        email: formValue.email,
        phoneNumber: formValue.phoneNumber,
        zipCode: formValue.zipCode,
        addressLine1: formValue.addressLine1,
        addressLine2: formValue.addressLine2 || null,
        city: formValue.city,
        state: formValue.state,
        password: formValue.password
      };

      this.authService.registerStudent(registrationData).subscribe({
        next: (student) => {
          this.loading = false;
          this.notificationService.showSuccess('Registro realizado com sucesso!', 'Bem-vindo');
          this.router.navigate(['/auth/login']);
        },
        error: (err) => {
          this.loading = false;
          const errorMsg = err.error?.message || 'Erro ao realizar o registro. Tente novamente.';
          this.notificationService.showError(errorMsg, 'Erro no Registro');
        }
      });
    } else {
      this.notificationService.showWarning('Por favor, preencha todos os campos obrigatórios corretamente.', 'Atenção');
      this.registerForm.markAllAsTouched();
    }
  }

  get f() {
    return this.registerForm.controls;
  }
}
