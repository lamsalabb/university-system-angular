import {Component, effect, signal} from '@angular/core';
import {Enrollment} from '../../../services/enrollment';
import {AuthService} from '../../../services/auth.service';
import {FormsModule} from '@angular/forms';
import {Pagination} from '../../../shared/pagination/pagination';

@Component({
  selector: 'app-view-students-instructor',
  imports: [
    FormsModule,
    Pagination
  ],
  templateUrl: './view-students-instructor.html',
  styleUrl: './view-students-instructor.css',
})
export class ViewStudentsInstructor {
  enrollments = signal<any[]>([]);
  grades: string[] = ['A', 'B', 'C', 'D', 'F'];
  loading = signal(true);
  error = signal<string | null>(null);

  currentPage = signal(0);
  pageSize = signal(10);
  totalElements = signal(0);

  constructor(private enrollmentService:Enrollment, private authService:AuthService) {
    effect(() => {
      this.currentPage();
      this.pageSize();
      this.loadEnrollments();
    });
  }
  ngOnInit() {
    this.loadEnrollments();
  }

  loadEnrollments(){
    this.loading.set(true);
    const id = this.authService.userId;
    this.enrollmentService.getEnrollmentByInstructorId(id,this.currentPage(),this.pageSize()).subscribe({
      next: (data) => {
        this.enrollments.set(data.content);
        this.totalElements.set(data.totalElements);
        this.loading.set(false);
      },
      error: error => {
        this.error.set('Failed to load enrollments');
        this.loading.set(false);
      }
    })
  }

  updateGrade(enrollmentId: number, grade: string | null) {
    if (!grade) return;
    this.loading.set(true);
    this.enrollmentService.updateGrade(enrollmentId, grade)
      .subscribe(
        {
          next: data => {
            alert("Updated Grade.");
            this.loading.set(false);
          },
          error: error => {
            this.error.set('Failed to update grade');
            this.loading.set(false);
          }
        }
      );
  }
}
