import {Enrollment} from './enrollment.model';
import {TaskCategory} from './task-category.model';

export interface Task {
  id: string;
  enrollment: Enrollment;
  taskCategory: TaskCategory;
  description: string;
  logDate: string;
  timeSpentInMinutes: number;
}
