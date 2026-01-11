import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Fee} from '../../../services/fee';
import {User} from '../../../services/user';

@Component({
  selector: 'app-register-fee',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './register-fee.html',
  styleUrl: './register-fee.css',
})
export class RegisterFee {
  feeForm: FormGroup;
  students = signal<any[]>([]);

  constructor(private fb: FormBuilder, private feeService: Fee, private userService: User) {
    this.loadStudents();
    this.feeForm = this.fb.group({
      studentId: [null, Validators.required],
      amount: ['', Validators.required],
      type: ['', Validators.required],
      dueDate: ['', Validators.required],
    })

  }

  loadStudents() {
    this.userService.getAllUsersByRole('STUDENT')
      .subscribe(users => {
        this.students.set(users);
      });
  }

  submit() {
    if (this.feeForm.invalid) {
      return;
    }

    this.feeService.createFee(this.feeForm.value).subscribe({
      next: () => {
        alert("Fee created successfully!");
      },
      error: () => {
        alert("Failed to register.");
      }
    })
  }

}
