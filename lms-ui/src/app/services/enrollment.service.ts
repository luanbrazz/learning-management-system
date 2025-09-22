import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseService } from './base.service';
import { Enrollment } from '../models/enrollment.model';
import { Observable } from 'rxjs';
import { EnrollmentRequest } from '../models/enrollment-request.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService extends BaseService<Enrollment, string> {
  private readonly enrollUrl = `${environment.apiUrl}/enrollments/enroll`;
  private readonly completeUrl = (id: string) => `${environment.apiUrl}/enrollments/${id}/complete`;
  private readonly expireUrl = `${environment.apiUrl}/enrollments/expire`;

  constructor(http: HttpClient) {
    super(http, 'enrollments');
  }

  enrollStudent(request: EnrollmentRequest): Observable<Enrollment> {
    return this.http.post<Enrollment>(this.enrollUrl, request);
  }

  completeCourse(enrollmentId: string): Observable<Enrollment> {
    return this.http.patch<Enrollment>(this.completeUrl(enrollmentId), {});
  }

  updateExpiredEnrollments(enrollmentIds: string[]): Observable<void> {
    return this.http.patch<void>(this.expireUrl, enrollmentIds);
  }
}
