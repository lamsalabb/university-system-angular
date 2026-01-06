import {Component, signal} from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-instructor-dashboard',
  imports: [
    RouterLink
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class InstructorDashboard {
  instructorName = signal<string | null>(localStorage.getItem('firstName'));

  cards = signal([
    {
      title: 'My Courses',
      description: 'View the courses you teach.',
      route: '/instructor/view-courses',
      button: 'View Courses',
    },
    {
      title: 'Students',
      description: 'View enrolled students by course and semester.',
      route: '/instructor/view-students',
      button: 'View Students',
    },
    {
      title: 'Attendance',
      description: 'Mark and review student attendance records.',
      route: '/instructor/view-attendance',
      button: 'View Attendance',
    },

    {
      title: 'Profile',
      description: 'View your account and contact information.',
      route: '/instructor/view-profile',
      button: 'View Profile',
    },
  ]);
}
