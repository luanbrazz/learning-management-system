import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task.model';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class TaskService extends BaseService<Task, string> {
  constructor(http: HttpClient) {
    super(http, 'tasks');
  }

  getTasksByEnrollmentId(enrollmentId: string): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/${this.endpoint}/enrollment/${enrollmentId}`);
  }
}
