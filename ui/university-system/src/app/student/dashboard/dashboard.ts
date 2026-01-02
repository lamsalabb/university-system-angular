import {Component, signal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-student-dashboard',
  imports: [
    RouterLink
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class StudentDashboard {
  studentName = signal('Student');

  cards = signal([
    {
      title: 'My Courses',
      description: 'View enrolled courses.',
      route: '/student/courses',
      button: 'View Courses',
    },
    {
      title: 'Attendance',
      description: 'Track attendance records and session summaries.',
      route: '/student/attendance',
      button: 'View Attendance',
    },
    {
      title: 'Grades',
      description: 'Review grades and academic performance.',
      route: '/student/grades',
      button: 'View Grades',
    },
    {
      title: 'Fees',
      description: 'Check outstanding balances and payment status.',
      route: '/student/fees',
      button: 'View Fees',
    },
    {
      title: 'Profile',
      description: 'View your personal and contact information.',
      route: '/student/profile',
      button: 'View Profile',
    },
  ]);
}
