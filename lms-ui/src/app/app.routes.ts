import {Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CourseListComponent} from './courses/course-list/course-list.component';
import {CourseFormComponent} from './courses/course-form/course-form.component';
import {CourseBrowserComponent} from './student/course-browser/course-browser.component';
import {EnrollmentListComponent} from './student/enrollment-list/enrollment-list.component';
import {TaskListComponent} from './student/task-list/task-list.component';
import {TaskFormComponent} from './tasks/task-form/task-form.component';
import {AuthGuard} from './auth.guard';
import {AdminEnrollmentsComponent} from './admin-enrollments/admin-enrollments.component';
import {TaskViewComponent} from './student/taskView/task-view.component';

export const routes: Routes = [
  {path: '', redirectTo: 'auth', pathMatch: 'full'},
  {path: 'auth', loadChildren: () => import('./auth/auth.routes').then(m => m.routes)},
  {
    path: 'admin-dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_ADMIN']}
  },
  {
    path: 'courses',
    component: CourseListComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_ADMIN']}
  },
  {
    path: 'courses/new',
    component: CourseFormComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_ADMIN']}
  },
  {
    path: 'courses/:id/edit',
    component: CourseFormComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_ADMIN']}
  },
  {
    path: 'student-dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_STUDENT']}
  },
  {
    path: 'student/courses',
    component: CourseBrowserComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_STUDENT']}
  },
  {
    path: 'student/enrollments',
    children: [
      {
        path: '',
        component: EnrollmentListComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ROLE_STUDENT', 'ROLE_ADMIN']}
      },
      {
        path: ':enrollmentId/tasks',
        component: TaskListComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ROLE_STUDENT', 'ROLE_ADMIN']}
      },
      {
        path: ':enrollmentId/tasks/new',
        component: TaskFormComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ROLE_STUDENT']}
      },
      {
        path: ':enrollmentId/tasks/:taskId/edit',
        component: TaskFormComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ROLE_STUDENT']}
      },
      {
        path: ':enrollmentId/tasks/:taskId/view',
        component: TaskViewComponent,
        canActivate: [AuthGuard],
        data: {roles: ['ROLE_STUDENT', 'ROLE_ADMIN']}
      }
    ]
  },
  {
    path: 'admin/enrollments',
    component: AdminEnrollmentsComponent,
    canActivate: [AuthGuard],
    data: {roles: ['ROLE_ADMIN']}
  }
];
