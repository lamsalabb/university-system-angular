import {Component} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-register-user',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './register-user.html',
  styleUrl: './register-user.css',
})
export class RegisterUser {

  private apiUrl = `${environment.url}/api/users`;

  userForm:FormGroup;


  constructor(private fb: FormBuilder, private http:HttpClient) {
    this.userForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['STUDENT', Validators.required],
      firstName: ['',[Validators.required]],
      lastName: ['',[Validators.required]],
    });
  }

  submit(){
    if(this.userForm.invalid){
      return;
    }

    this.http.post(this.apiUrl, this.userForm.value)
      .subscribe({
        next: (res)=>{
          alert("User registered successfully!");
          this.userForm.reset({role:'STUDENT'});
        },
        error: (err)=>{
          alert("Failed to register.");
        }
      });
  }



}
