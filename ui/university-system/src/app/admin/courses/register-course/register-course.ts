import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Course} from '../../../services/course';
import {User} from '../../../services/user';
import {SnackbarService} from '../../../shared/toast/snackbar-service';
import {MatProgressSpinner} from '@angular/material/progress-spinner';

@Component({
  selector: 'app-register-course',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    MatProgressSpinner
  ],
  templateUrl: './register-course.html',
  styleUrl: './register-course.css',
})
export class RegisterCourse {
  courseForm: FormGroup;
  instructors = signal<any[]>([]);
  loading = signal(false);
  submitted = signal(false);

  constructor(private fb: FormBuilder, private courseService: Course, private userService: User, private snackBar: SnackbarService) {
    this.loadInstructors();
    this.courseForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      code: ['', Validators.required],
      credits: ['', Validators.required],
      cost: ['', Validators.required],
      instructorId: [null, Validators.required]
    });
  }

  loadInstructors() {
    this.userService.getAllUsersByRole('INSTRUCTOR').subscribe(users => {
      this.instructors.set(users);
    });
  }

  submit() {
    this.submitted.set(true);

    if (this.courseForm.invalid) {
      return;
    }

    this.loading.set(true);

    this.courseService.createCourse(this.courseForm.value).subscribe({
      next: () => {
        this.loading.set(false);
        this.snackBar.show("Course created successfully!", "success");
        this.courseForm.reset();
        this.submitted.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.snackBar.show("Failed to register.", "error");
      }
    });
  }
}
