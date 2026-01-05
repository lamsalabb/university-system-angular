import {Component, signal} from '@angular/core';
import {Enrollment} from '../../../services/enrollment';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-view-students-instructor',
  imports: [],
  templateUrl: './view-students-instructor.html',
  styleUrl: './view-students-instructor.css',
})
export class ViewStudentsInstructor {
  enrollments = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private enrollmentService:Enrollment, private authService:AuthService) {
  }
  ngOnInit() {
    this.loadEnrollments();
  }

  loadEnrollments(){
    this.loading.set(true);
    const id = this.authService.userId;
    this.enrollmentService.getEnrollmentByInstructorId(id).subscribe({
      next: data => {
        this.enrollments.set(data);
        this.loading.set(false);
      },
      error: error => {
        this.error.set('Failed to load enrollments');
        this.loading.set(false);
      }
    })
  }
}
