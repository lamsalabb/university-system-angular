import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Course} from '../../../services/course';
import {User} from '../../../services/user';

@Component({
  selector: 'app-register-course',
  imports: [
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './register-course.html',
  styleUrl: './register-course.css',
})
export class RegisterCourse {
  courseForm: FormGroup;
  instructors = signal<any[]>([]);

  constructor(private fb: FormBuilder, private courseService: Course, private userService: User) {
    this.loadInstructors();
    this.courseForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      code: ['', Validators.required],
      credits: ['', Validators.required],
      instructorId: [null, Validators.required]
    })
  }

  loadInstructors() {
    this.userService.getAllUsersByRole('INSTRUCTOR').subscribe(users => {
      this.instructors.set(users);
    });
  }

  submit() {
    if (this.courseForm.invalid) {
      return;
    }

    this.courseService.createCourse(this.courseForm.value).subscribe({
      next: () => {
        alert("Course created successfully!");
      },
      error: () => {
        alert("Failed to register.");
      }
    })
  }

}
