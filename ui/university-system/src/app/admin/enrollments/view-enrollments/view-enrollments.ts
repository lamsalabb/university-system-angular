import {Component, effect, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Enrollment} from '../../../services/enrollment';
import {User} from '../../../services/user';
import {Course} from '../../../services/course';
import {Pagination} from '../../../shared/pagination/pagination';
import {SnackbarService} from '../../../shared/toast/snackbar-service';

@Component({
  selector: 'app-view-enrollments',
  imports: [
    ReactiveFormsModule,
    Pagination
  ],
  templateUrl: './view-enrollments.html',
  styleUrl: './view-enrollments.css',
})
export class ViewEnrollments {
  enrollmentForm!: FormGroup;
  enrollments = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);
  students = signal<any[]>([]);
  courses = signal<any[]>([]);
  currentPage = signal(0);
  pageSize = signal(10);
  totalElements = signal(0);

  constructor(private fb: FormBuilder, private enrollmentService: Enrollment, private userService: User, private courseService: Course, private snackBar: SnackbarService) {
    effect(() => {
      this.currentPage();
      this.pageSize();
      this.loadEnrollments();
    });

  }

  ngOnInit() {
    this.loadStudents();
    this.loadCourses();
    this.loadEnrollments();

    this.enrollmentForm = this.fb.group({
      studentId: ['', Validators.required],
      courseId: ['', Validators.required],
      semester: ['', Validators.required],
    });

    this.enrollmentForm.patchValue({
      semester: 'SPRING 2026'
    });
  }


  loadStudents() {
    this.userService.getAllUsersByRole('STUDENT').subscribe(users => {
      this.students.set(users);
    })
  }

  loadCourses() {
    this.courseService.getAllCourses().subscribe(courses => {
      this.courses.set(courses);
    })
  }

  loadEnrollments() {
    this.loading.set(true);

    this.enrollmentService
      .getAllEnrollments(this.currentPage(), this.pageSize())
      .subscribe({
        next: res => {
          this.enrollments.set(res.content);
          this.totalElements.set(res.totalElements);
          this.loading.set(false);
        },
        error: () => {
          this.error.set("Failed to load enrollments");
          this.loading.set(false);
        }
      });
  }


  enroll() {
    if (this.enrollmentForm.invalid) return;

    this.enrollmentService
      .enroll(this.enrollmentForm.value)
      .subscribe(
        {
          next: res => {
            this.enrollments.set([
              ...this.enrollments(), res
            ]);
            this.snackBar.show("Successfully enrolled.", "success");
          },
          error: err => {
            this.error.set("Failed to enroll");
            this.snackBar.show(err.error.message, "error");
          }
        }
      );
  }

  dropEnrollment(id: number) {
    if (!confirm('Drop this enrollment?')) return;

    this.enrollmentService
      .drop(id)
      .subscribe(() => {
        this.enrollments.set(
          this.enrollments().map(e =>
            e.id === id ? {...e, status: 'DROPPED'} : e
          )
        );
        this.snackBar.show("Successfully dropped.", "info");
      });
  }


}
