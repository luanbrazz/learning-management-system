import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BaseService} from './base.service';
import {TaskCategory} from '../models/task-category.model';

@Injectable({
  providedIn: 'root'
})
export class TaskCategoryService extends BaseService<TaskCategory, string> {
  constructor(http: HttpClient) {
    super(http, 'task-categories');
  }
}
