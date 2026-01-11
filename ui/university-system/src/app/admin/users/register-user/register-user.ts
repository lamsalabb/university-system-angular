import {Component} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {User} from '../../../services/user';

@Component({
  selector: 'app-register-user',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './register-user.html',
  styleUrl: './register-user.css',
})
export class RegisterUser {

  userForm: FormGroup;


  constructor(private fb: FormBuilder, private userService: User) {
    this.userForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['STUDENT', Validators.required],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
    });
  }

  submit() {
    if (this.userForm.invalid) {
      return;
    }

    this.userService.createUser(this.userForm.value)
      .subscribe({
        next: (res) => {
          alert("User registered successfully!");
          this.userForm.reset({role: 'STUDENT'});
        },
        error: (err) => {
          alert("Failed to register.");
        }
      });
  }
}
