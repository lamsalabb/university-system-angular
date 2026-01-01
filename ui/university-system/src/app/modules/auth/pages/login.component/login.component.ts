import { Component } from '@angular/core';
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
  loading= false;
  errorMessage = "";

  constructor(
    private fb:FormBuilder,
    private authService:AuthService,
    private router:Router
  ) {this.loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  }



  login(){
    if(this.loginForm.invalid){//validation check
      return;
    }

    this.loading=true;
    this.errorMessage='';

    this.authService.login(this.loginForm.value).subscribe(
      {
        next: (res)=>{
          this.authService.handleLoginSuccess(res);
        },
        error: (err) =>{
          this.errorMessage = "Invalid email or password";
          this.loading = false;
        }
      }
    );


  }
}
