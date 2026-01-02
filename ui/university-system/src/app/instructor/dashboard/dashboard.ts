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
  instructorName = signal('Instructor');

  cards = signal([
    {
      title: 'My Courses',
      description: 'View the courses you teach.',
      route: '/instructor/courses',
      button: 'View Courses',
    },
    {
      title: 'Students',
      description: 'View enrolled students by course and semester.',
      route: '/instructor/students',
      button: 'View Students',
    },
    {
      title: 'Attendance',
      description: 'Mark and review student attendance records.',
      route: '/instructor/attendance',
      button: 'View Attendance',
    },
    {
      title: 'Grades',
      description: 'Assign and update student grades.',
      route: '/instructor/grades',
      button: 'Manage Grades',
    },
    {
      title: 'Profile',
      description: 'View your account and contact information.',
      route: '/instructor/profile',
      button: 'View Profile',
    },
  ]);
}
