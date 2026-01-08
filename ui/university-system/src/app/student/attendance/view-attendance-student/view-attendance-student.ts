import {Component, computed, signal} from '@angular/core';
import {BaseChartDirective} from 'ng2-charts';
import {Attendance} from '../../../services/attendance';
import {AuthService} from '../../../services/auth.service';
import {ChartData} from 'chart.js';
import {DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-view-attendance-student',
  standalone: true,
  imports: [BaseChartDirective, DecimalPipe],
  templateUrl: './view-attendance-student.html',
  styleUrl: './view-attendance-student.css',
})
export class ViewAttendanceStudent {

  attendance = signal<any[]>([]);
  courses = signal<{ id: number; title: string }[]>([]);
  summary = signal<any | null>(null);

  selectedCourseId = signal<number | null>(null);

  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private attendanceService: Attendance, private authService: AuthService) {}

  ngOnInit() {
    this.loadAttendance();
  }

  loadAttendance() {
    const id = this.authService.userId;

    this.loading.set(true);
    this.error.set(null);
    this.courses.set([]);
    this.summary.set(null);

    this.attendanceService.getByStudent(id).subscribe({
      next: res => {
        this.attendance.set(res);

        res.forEach(a => {
          this.courses.update(list => {
            if (list.some(c => c.id === a.course.id)) return list;
            return [...list, { id: a.course.id, title: a.course.title }];
          });
        });

        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load attendance');
        this.loading.set(false);
      }
    });
  }

  loadSummary(courseId: number) {
    const studentId = this.authService.userId;

    this.selectedCourseId.set(courseId);
    this.loading.set(true);
    this.error.set(null);

    this.attendanceService
      .getSummaryByStudent(studentId, courseId)
      .subscribe({
        next: res => {
          this.summary.set(res);
          this.loading.set(false);
        },
        error: () => {
          this.error.set('Failed to load summary');
          this.loading.set(false);
        }
      });
  }


  chartData = computed<ChartData<'pie'> | undefined>(() => {
    const s = this.summary();
    if (!s) return undefined;//if response is empty

    return {
      labels: ['Present', 'Absent', 'Excused'],
      datasets: [
        {
          data: [
            s.presentCount,
            s.absentCount,
            s.excusedCount
          ],
          backgroundColor: undefined
        }
      ]
    };
  });
}
