import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

import { Student } from '../models/student.model';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../models/login-request.model';
import { LoginResponse } from '../models/login-response.model';
import { StudentRegistrationRequest } from '../models/student-registration-request.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = `${environment.apiUrl}/auth`;
  private readonly registerUrl = `${environment.apiUrl}/students/register`;

  private readonly tokenKey = 'access_token';
  private readonly roleKey = 'user_role';
  private readonly fullNameKey = 'user_full_name';
  private readonly userIdKey = 'user_id';
  private readonly studentIdKey = 'student_id';

  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  private isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => this.saveLoginData(response))
    );
  }

  registerStudent(registrationData: StudentRegistrationRequest): Observable<Student> {
    return this.http.post<Student>(this.registerUrl, registrationData);
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem(this.roleKey);
      localStorage.removeItem(this.fullNameKey);
      localStorage.removeItem(this.userIdKey);
      localStorage.removeItem(this.studentIdKey);
    }
  }

  getToken(): string | null {
    return this.isBrowser() ? localStorage.getItem(this.tokenKey) : null;
  }

  getRole(): string | null {
    return this.isBrowser() ? localStorage.getItem(this.roleKey) : null;
  }

  getFullName(): string | null {
    return this.isBrowser() ? localStorage.getItem(this.fullNameKey) : null;
  }

  getUserId(): string | null {
    return this.isBrowser() ? localStorage.getItem(this.userIdKey) : null;
  }

  getStudentId(): string | null {
    return this.isBrowser() ? localStorage.getItem(this.studentIdKey) : null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    return this.getRole() === 'ROLE_ADMIN';
  }

  isStudent(): boolean {
    return this.getRole() === 'ROLE_STUDENT';
  }

  private saveLoginData(response: LoginResponse): void {
    if (this.isBrowser()) {
      localStorage.setItem(this.tokenKey, response.access_token);
      localStorage.setItem(this.roleKey, response.role);
      localStorage.setItem(this.fullNameKey, response.full_name);
      localStorage.setItem(this.userIdKey, response.user_id);
      if (response.student_id) {
        localStorage.setItem(this.studentIdKey, response.student_id);
      }
    }
  }
}
