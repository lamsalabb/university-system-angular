import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {User} from '../../../services/user';
import {SnackbarService} from '../../../shared/toast/snackbar-service';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@Component({
  selector: 'app-register-user',
  imports: [
    ReactiveFormsModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './register-user.html',
  styleUrl: './register-user.css',
})
export class RegisterUser {

  userForm: FormGroup;
  loading = signal(false);
  submitted = signal(false);

  constructor(
    private fb: FormBuilder,
    private userService: User,
    private snackBar: SnackbarService
  ) {
    this.userForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['STUDENT', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
    });
  }

  submit() {
    this.submitted.set(true);

    if (this.userForm.invalid) return;

    this.loading.set(true);

    this.userService.createUser(this.userForm.value)
      .subscribe({
        next: () => {
          this.loading.set(false);
          this.snackBar.show("User registered successfully!", "success");
          this.userForm.reset({role: 'STUDENT'});
          this.submitted.set(false);
        },
        error: () => {
          this.loading.set(false);
          this.snackBar.show("Failed to register.", "error");
        }
      });
  }
}
