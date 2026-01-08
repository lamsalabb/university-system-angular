import {Component, computed, signal} from '@angular/core';
import {Course} from '../../../services/course';
import {FormsModule} from '@angular/forms';
import {User} from '../../../services/user';
import {RouterLink} from '@angular/router';
import {Enrollment} from '../../../services/enrollment';
import {Reporting} from '../../../services/reporting';
import {ChartData} from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';

@Component({
  selector: 'app-view-courses',
  imports: [
    FormsModule,
    RouterLink,
    BaseChartDirective
  ],
  templateUrl: './view-courses.html',
  styleUrl: './view-courses.css',
})
export class ViewCourses {
  courses = signal<any[]>([]);
  enrollments = signal<any[]>([]);

  loading = signal(true);
  error = signal<string | null>(null);
  instructors: any[] = [];

  showDistribution = signal(false);
  distributionData = signal<any[] | null>(null);

  constructor(private courseService: Course, private userService:User, private enrollmentService: Enrollment, private reportingService:Reporting) {

  }

  ngOnInit() {
    this.loadCourses();
    this.loadInstructors();
  }


  loadCourses() {
    this.loading.set(true);

    this.courseService.getAllCourses().subscribe({
      next: (courses) => {
        this.courses.set(courses);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load courses');
        this.loading.set(false);
      },
    });
  }

  loadInstructors() {
    this.userService.getAllUsersByRole('INSTRUCTOR').subscribe(users => {
      this.instructors = users;
    });

  }

  deleteCourse(id: number) {
    if (!confirm('Delete this course?')) return;

    this.courseService.deleteCourse(id).subscribe(() => {
      this.courses.update(c => c.filter(x => x.id !== id));
    });
  }

  editingCourse = signal<any | null>(null);
  detailsCourse = signal<any | null>(null);
  studentsEnrolled = signal<any | null>(null);



  editForm = {
    title: '',
    code: '',
    credits: 0,
    description: '',
    instructorId: 0
  };


  openEdit(c: any) {
    this.editingCourse.set(c);

    this.editForm = {
      title: c.title,
      code: c.code,
      credits: c.credits,
      description: c.description,
      instructorId: c.instructorId
    };
  }

  openDetails(c:any){
    this.detailsCourse.set(c);
    this.enrollmentService.getEnrollmentByCourse(c).subscribe({
      next: enrollments => {
        this.studentsEnrolled.set(enrollments);
      },
      error: err => {
        this.error.set('Failed to load enrollments');
        this.loading.set(false);
      },
    })
  }

  closeDetails() {
    this.detailsCourse.set(null);
  }


  closeEdit() {
    this.editingCourse.set(null);
  }

  saveEdit() {
    const payload = { ...this.editForm };//copying form data into another object

    this.courseService
      .updateCourse(this.editingCourse()!.id, payload)
      .subscribe(updated => {
        this.courses.update(list =>
          list.map(c => c.id === updated.id ? updated : c)
        );
        this.closeEdit();
      });
  }

  canSaveCourse(): boolean {
    return !!(
      this.editForm.title &&
      this.editForm.code &&
      this.editForm.credits &&
      this.editForm.instructorId
    );
  }

  openDistribution() {
    this.showDistribution.set(true);

    if (!this.distributionData()) {
      this.reportingService.getCourseEnrollment().subscribe(data => {
        this.distributionData.set(data);
      });
    }
  }

  closeDistribution() {
    this.showDistribution.set(false);
  }

  distributionChartData = computed<ChartData<'pie'> | undefined>(() => {
    const data = this.distributionData();
    if (!data) return undefined;

    return {
      labels: data.map(d => d.courseCode),
      datasets: [
        {
          data: data.map(d => d.enrollmentCount),
          backgroundColor: undefined
        }
      ]
    };
  });


  protected generateReportPdf() {
    this.reportingService.getPdf().subscribe({
      next: blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'average-grades.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
        alert("Download Successful.");

      },
      error: err => {
        alert("Failed to download. Try again later.");
        this.loading.set(false);
      }
    })
  }
}
