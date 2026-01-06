import {Component, signal} from '@angular/core';
import {Enrollment} from '../../../services/enrollment';
import {Attendance} from '../../../services/attendance';
import {Router} from '@angular/router';

@Component({
  selector: 'app-mark-attendance-instructor',
  imports: [],
  templateUrl: './mark-attendance-instructor.html',
  styleUrl: './mark-attendance-instructor.css',
})
export class MarkAttendanceInstructor {

  courses = signal<{ id: number; title: string }[]>([]);
  enrollments = signal<any[]>([]);
  selectedCourseId = signal<number | null>(null);
  today = new Date().toISOString().slice(0, 10);



  constructor(private enrollmentService:Enrollment, private attendanceService:Attendance) { }

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
        this.enrollments.set(
          res.map(e => ({ ...e, status: 'PRESENT' }))
        );
      });
  }
  selectCourse(courseId: string){
    const id = Number(courseId);
    if (!id) return;

    this.selectedCourseId.set(id);
    this.loadEnrollments(id);
  }

  resetCourse() {
    this.selectedCourseId.set(null);
    this.enrollments.set([]);
  }

  setStatus(enrollmentId: number, status: string){

  }

  saveAttendance() {
    const today = new Date().toISOString().slice(0, 10);

    this.enrollments().forEach(e => {
      this.attendanceService.markAttendance({
        enrollmentId: e.id,
        sessionDate: today,
        status: e.status,
        remarks: null
      }).subscribe({
        next: () => {
          alert("Attendance Submitted.");
          this.selectedCourseId.set(-1);
        }
        }

      );
    });
  }




}
