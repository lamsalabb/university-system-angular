import {Component, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {Attendance} from '../../../services/attendance';
import {AuthService} from '../../../services/auth.service';
import {Course} from '../../../services/course';

@Component({
  selector: 'app-view-attendance-instructor',
  imports: [
    FormsModule
  ],
  templateUrl: './view-attendance-instructor.html',
  styleUrl: './view-attendance-instructor.css',
})
export class ViewAttendanceInstructor {

  attendance = signal<any[]>([]);
  courses = signal<{ id: number; title: string }[]>([]);

  loading = signal(true);
  error = signal<string | null>(null);

  selectedCourseId = signal<number | null>(null);

  constructor(private attendanceService:Attendance, private authService: AuthService, private courseService:Course,private router:Router) { }

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
        this.error.set(error.message);
        this.loading.set(false);
      }
    });
  }

  loadAttendance(courseId: number) {
    this.selectedCourseId.set(courseId);
    this.loading.set(true);
    this.error.set(null);

    this.attendanceService.getByCourse(courseId).subscribe({
      next: res => {
        this.attendance.set(res);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load attendance');
        this.loading.set(false);
      }
    });
  }

  updateAttendance(id: number, status: string) {
    this.attendanceService.updateAttendance(id, status).subscribe({
      next: (result) => {
        alert("Updated attendance successfully!");
      },
      error: (error) => {
        this.error.set(error.message);
        this.loading.set(false);
        alert("Could not update attendance.");

      }
      }
    );
  }

  goToMarkAttendance() {
    this.router.navigate(
      ['/instructor/mark-attendance'],
      {
        state: {
          courses: this.courses()
        }
      }
    );
  }
}
