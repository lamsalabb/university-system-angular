import {Component, signal} from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  imports: [
    RouterLink
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class AdminDashboard {
  cards = signal([
    {
      title: 'User Management',
      description: 'Register users, manage roles, update profiles, and control access.',
      route: '/admin/view-users',
      button: 'Manage Users'
    },
    {
      title: 'Course Management',
      description: 'Create, update, delete courses and assign instructors.',
      route: '/admin/view-courses',
      button: 'Manage Courses'
    },
    {
      title: 'Enrollment Management',
      description: 'Enroll students, drop courses, and view enrollments.',
      route: '/admin/enrollments',
      button: 'Manage Enrollments'
    },
    {
      title: 'Fee Management',
      description: 'Track payments, unpaid balances, and create new fees.',
      route: '/admin/view-fees',
      button: 'Manage Fees'
    },
    {
      title: 'Analytics & Insights',
      description: 'Enrollment trends, grade distributions, and active student counts.',
      route: '/admin/analytics',
      button: 'View Analytics'
    }
  ]);
}
