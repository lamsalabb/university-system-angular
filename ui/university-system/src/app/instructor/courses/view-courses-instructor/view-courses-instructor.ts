import {Component, signal} from '@angular/core';
import {Course} from '../../../services/course';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-view-courses-instructor',
  imports: [],
  templateUrl: './view-courses-instructor.html',
  styleUrl: './view-courses-instructor.css',
})
export class ViewCoursesInstructor {
  courses = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private courseService: Course, private authService: AuthService) {
  }

  ngOnInit() {
    this.loadCourses();
  }

  loadCourses() {
    this.loading.set(true);
    const id = this.authService.userId;
    this.courseService.getCoursesByInstructorId(id).subscribe({
      next: data => {
        this.courses.set(data);
        this.loading.set(false);
      },
      error: error => {
        this.error.set('Failed to load courses');
        this.loading.set(false);
      }
    });
  }
}
