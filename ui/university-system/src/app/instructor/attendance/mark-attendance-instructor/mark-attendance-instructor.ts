import { Component, signal, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { Enrollment } from '../../../services/enrollment';
import { Attendance } from '../../../services/attendance';
import { SnackbarService } from '../../../shared/toast/snackbar-service';

@Component({
  selector: 'app-mark-attendance-instructor',
  imports: [FormsModule],
  templateUrl: './mark-attendance-instructor.html',
  styleUrl: './mark-attendance-instructor.css',
})
export class MarkAttendanceInstructor implements OnInit {
  courses = signal<{ id: number; title: string }[]>([]);
  enrollments = signal<any[]>([]);
  selectedCourseId = signal<number | null>(null);

  today = new Date().toISOString().slice(0, 10);

  statusMap: Record<number, string> = {};
  remarksMap: Record<number, string> = {};

  constructor(
    private enrollmentService: Enrollment,
    private attendanceService: Attendance,
    private router: Router,
    private snackBar: SnackbarService
  ) {}

  ngOnInit(): void {
    const state = history.state;
    if (state?.courses) {
      this.courses.set(state.courses);
    }
  }

  selectCourse(courseId: string): void {
    const id = Number(courseId);
    if (!id) return;

    this.selectedCourseId.set(id);
    this.loadEnrollments(id);
  }

  loadEnrollments(courseId: number): void {
    this.enrollmentService.getEnrollmentByCourse(courseId).subscribe(res => {
      this.enrollments.set(res);

      res.forEach((e: any) => {
        // default status + default remarks
        this.statusMap[e.id] = 'PRESENT';
        this.remarksMap[e.id] = '';
      });
    });
  }

  resetCourse(): void {
    this.selectedCourseId.set(null);
    this.enrollments.set([]);
    this.statusMap = {};
    this.remarksMap = {};
  }

  saveAttendance(): void {
    const sessionDate = new Date().toISOString().slice(0, 10);

    const payload = this.enrollments().map((e: any) => {
      const status = this.statusMap[e.id] ?? 'PRESENT';
      const remarks =
        status === 'EXCUSED' ? (this.remarksMap[e.id]?.trim() || null) : null;

      return {
        enrollmentId: e.id,
        sessionDate,
        status,
        remarks,
      };
    });

    this.attendanceService.markAttendanceBatch(payload).subscribe({
      next: () => {
        this.snackBar.show('Attendance saved successfully.', 'success');
        this.router.navigate(['/instructor/dashboard']);
      },
      error: err => {
        if (err?.status === 409) {
          this.snackBar.show(err.error?.message ?? 'Conflict', 'error');
        } else {
          this.snackBar.show('Attendance saved failed.', 'error');
        }
      },
    });
  }
}
