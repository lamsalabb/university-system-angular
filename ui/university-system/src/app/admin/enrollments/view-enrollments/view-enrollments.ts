import {Component, computed, effect, signal} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Enrollment} from '../../../services/enrollment';
import {User} from '../../../services/user';
import {Course} from '../../../services/course';
import {Pagination} from '../../../shared/pagination/pagination';

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

  constructor(private fb: FormBuilder, private enrollmentService:Enrollment, private userService:User, private courseService:Course) {
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
      studentId: ['', [Validators.required]],
      courseId: ['', [Validators.required]],
      semester: ['', [Validators.required]],
    })
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
          },
          error: err => {
            this.error.set("Failed to enroll");
            alert(err.error.message);
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
            e.id === id ? { ...e, status: 'DROPPED' } : e
          )
        );
      });
  }

  studentNameById = computed(() => {
    const map: Record<number, string> = {};

    this.students().forEach(s => {
      map[s.id] = `${s.firstName} ${s.lastName}`;
    });

    return map;
  });

  courseLabelById = computed(() => {
    const map: Record<number, string> = {};
    this.courses().forEach(c => {
      map[c.id] = `${c.code}-${c.title}`;
    });
    return map;
  });



}
