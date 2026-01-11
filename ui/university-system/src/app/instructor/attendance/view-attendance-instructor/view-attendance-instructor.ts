import {Component, computed, effect, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {Attendance} from '../../../services/attendance';
import {AuthService} from '../../../services/auth.service';
import {Course} from '../../../services/course';
import {ChartData} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {DecimalPipe} from '@angular/common';
import {Pagination} from '../../../shared/pagination/pagination';

@Component({
  selector: 'app-view-attendance-instructor',
  imports: [
    FormsModule,
    BaseChartDirective,
    DecimalPipe,
    Pagination
  ],
  templateUrl: './view-attendance-instructor.html',
  styleUrl: './view-attendance-instructor.css',
})
export class ViewAttendanceInstructor {

  attendance = signal<any[]>([]);
  courses = signal<{ id: number; title: string }[]>([]);
  summary = signal<any | null>(null);

  flag: boolean = false;
  loading = signal(true);
  error = signal<string | null>(null);

  selectedCourseId = signal<number | null>(null);
  currentPage = signal(0);
  pageSize = signal(10);
  totalElements = signal(0);
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
  private lastSummaryCourseId: number | null = null;

  constructor(private attendanceService: Attendance, private authService: AuthService, private courseService: Course, private router: Router) {
    effect(() => {
      const courseId = this.selectedCourseId();
      this.currentPage();
      this.pageSize();

      if (courseId !== null) {
        this.loadAttendance(courseId);
      }
    });
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
        this.error.set(error.message);
        this.loading.set(false);
      }
    });
  }

  loadAttendance(courseId: number) {
    this.selectedCourseId.set(courseId);
    this.loading.set(true);
    this.error.set(null);

    if (this.lastSummaryCourseId !== courseId) {//for load summary to load only after unique course selection
      this.lastSummaryCourseId = courseId;
      this.loadSummary(courseId);
    }
    this.attendanceService.getByCourse(courseId, this.currentPage(), this.pageSize()).subscribe({
      next: (res) => {
        this.attendance.set(res.content);
        this.totalElements.set(res.totalElements);
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

  loadSummary(courseId: number) {

    this.selectedCourseId.set(courseId);
    this.loading.set(true);
    this.error.set(null);

    this.attendanceService
      .getSummaryByCourse(courseId)
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
}
