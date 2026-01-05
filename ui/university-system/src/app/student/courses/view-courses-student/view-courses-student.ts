import {Component, signal} from '@angular/core';
import {Enrollment} from '../../../services/enrollment';
import {Course} from '../../../services/course';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-view-courses-student',
  imports: [
  ],
  templateUrl: './view-courses-student.html',
  styleUrl: './view-courses-student.css',
})
export class ViewCoursesStudent {

  enrollments = signal<any[]>([]);
  courses = signal<{ course: any; enrollmentId: number, enrollmentStatus: string, enrollmentGrade: string }[]>([]);

  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private enrollmentService:Enrollment, private courseService:Course, private authService: AuthService) {

  }

  ngOnInit() {
    this.loadEnrollments();
  }

  loadEnrollments() {
    const studentId = this.authService.userId;

    this.loading.set(true);
    this.error.set(null);

    this.enrollmentService.getEnrollmentByStudent(studentId).subscribe({
      next: value => {
        this.enrollments.set(value);
        this.loadCourses();
      },
      error: () => {
        this.error.set('Failed to load enrollments');
        this.loading.set(false);
      }
    });
  }

  loadCourses() {

    this.courses.set([]);
    const enrollments = this.enrollments();
    let loaded = 0;

    if (enrollments.length === 0) {
      this.loading.set(false);
      return;
    }

    for (const enrollment of enrollments) {
      this.courseService.getCourseById(enrollment.course.id).subscribe({
        next: course => {
          this.courses.update(courses => [
            ...courses,
            {
              course,
              enrollmentId: enrollment.id,
              enrollmentStatus: enrollment.status,
              enrollmentGrade: enrollment.grade,
            }
          ]);

          loaded++;

          if (loaded === enrollments.length) {
            this.loading.set(false);
          }
        },
        error: () => {
          this.error.set('Failed to load courses');
          this.loading.set(false);
        }
      });
    }

  }

  dropEnrollment(id: number) {
    if (!confirm('Drop this enrollment?')) return;

    this.enrollmentService.drop(id).subscribe({
      next: () => {
        this.courses.update(courses =>
          courses.map(c =>
            c.enrollmentId === id
              ? { ...c, enrollmentStatus: 'DROPPED' }
              : c
          )
        );
      }
    });
  }



}
