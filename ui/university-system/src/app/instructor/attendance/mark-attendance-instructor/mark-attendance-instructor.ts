import {Component, signal} from '@angular/core';
import {Enrollment} from '../../../services/enrollment';
import {Attendance} from '../../../services/attendance';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-mark-attendance-instructor',
  imports: [
    FormsModule
  ],
  templateUrl: './mark-attendance-instructor.html',
  styleUrl: './mark-attendance-instructor.css',
})
export class MarkAttendanceInstructor {

  courses = signal<{ id: number; title: string }[]>([]);
  enrollments = signal<any[]>([]);
  selectedCourseId = signal<number | null>(null);
  today = new Date().toISOString().slice(0, 10);
  statusMap: Record<number, string> = {};


  constructor(private enrollmentService: Enrollment, private attendanceService: Attendance, private router: Router) {
  }

  ngOnInit() {
    const state = history.state;

    if (state?.courses) {
      this.courses.set(state.courses);
    }
  }

  loadEnrollments(courseId: number) {
    this.enrollmentService
      .getEnrollmentByCourse(courseId)
      .subscribe(res => {
        this.enrollments.set(res);

        res.forEach(e => {
          this.statusMap[e.id] = 'PRESENT';
        });
      });
  }

  selectCourse(courseId: string) {
    const id = Number(courseId);
    if (!id) return;

    this.selectedCourseId.set(id);
    this.loadEnrollments(id);
  }

  resetCourse() {
    this.selectedCourseId.set(null);
    this.enrollments.set([]);
  }


  saveAttendance() {
    this.attendanceService.markAttendanceBatch(
      this.enrollments().map(e => ({
        enrollmentId: e.id,
        sessionDate: new Date().toISOString().slice(0, 10),
        status: this.statusMap[e.id],
        remarks: null
      }))
    ).subscribe({
        next: () => {
          alert("Attendance saved successfully.");
          this.router.navigate(['/instructor/dashboard']);
        },
        error: err => {
          if (err.status === 409) {
            alert(err.error.message);
          } else {
            alert("Attendance saved failed.");
          }

        }
      }
    );
  }


}
