import { Student } from './student.model';
import { Course } from './course.model';

export interface Enrollment {
  id: string;
  student: Student;
  course: Course;
  enrollmentDate: string;
  completionDate: string | null;
  status: 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED' | 'EXPIRED';
}
