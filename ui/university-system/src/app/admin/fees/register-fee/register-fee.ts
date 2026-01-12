import {Component, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Fee} from '../../../services/fee';
import {User} from '../../../services/user';
import {SnackbarService} from '../../../shared/toast/snackbar-service';

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
  submitted = signal(false);


  constructor(private fb: FormBuilder, private feeService: Fee, private userService: User, private snackBar: SnackbarService) {
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
    this.submitted.set(true);

    if (this.feeForm.invalid) {
      return;
    }

    this.feeService.createFee(this.feeForm.value).subscribe({
      next: () => {
        this.snackBar.show("Fee created successfully!", "success");
        this.feeForm.reset();
        this.submitted.set(false);
      },
      error: () => {
        this.snackBar.show("Failed to register.", "error");
      }
    });
  }

}
