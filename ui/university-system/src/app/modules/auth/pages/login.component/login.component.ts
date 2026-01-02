import {ChangeDetectorRef, Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-login.component',
  standalone:true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  loginForm:FormGroup;

  loading = signal(false);
  errorMessage = signal('');


  constructor(
    private fb:FormBuilder,
    private authService:AuthService,
    private router:Router,
    private cdr: ChangeDetectorRef,
  ) {this.loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  }

  login() {
    if (this.loginForm.invalid) return;

    this.loading.set(true);
    this.errorMessage.set('');

    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        this.loading.set(false);
        this.authService.handleLoginSuccess(res);
      },
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.status === 404 ? (err.error?.message ?? 'Invalid email or password') : 'Something went wrong.');
      }
    });
  }



}
