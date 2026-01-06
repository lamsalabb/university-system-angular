import {Component, signal} from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-student-dashboard',
  imports: [
    RouterLink
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class StudentDashboard {
  studentName = signal<string | null>(localStorage.getItem('firstName'));

  cards = signal([
    {
      title: 'My Courses',
      description: 'View enrolled courses and grades.',
      route: '/student/view-courses',
      button: 'View Courses',
    },
    {
      title: 'Attendance',
      description: 'Track attendance records and session summaries.',
      route: '/student/view-attendance',
      button: 'View Attendance',
    },

    {
      title: 'Fees',
      description: 'Check outstanding balances and payment status.',
      route: '/student/view-fees',
      button: 'View Fees',
    },
    {
      title: 'Profile',
      description: 'View your personal and contact information.',
      route: '/student/view-profile',
      button: 'View Profile',
    },
  ]);
}
