import {Component, computed, OnInit, signal} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Fee} from '../../../services/fee';
import {User} from '../../../services/user';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-view-fees',
  imports: [
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './view-fees.html',
  styleUrl: './view-fees.css',
})
export class ViewFees implements OnInit {
  fees = signal<any[]>([]);
  students = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);


  constructor(private feeService:Fee, private userService:User) {
  }

  ngOnInit() {
    this.loadFees();
    this.loadStudents();
  }

  loadFees(){
    this.loading.set(true);

    this.feeService.getAllFees().subscribe({
      next: fees => {
        this.fees.set(fees);
        this.loading.set(false);
      },
      error: error => {
        this.error.set('Failed to load fees');
        this.loading.set(false);
      }
    })
  }

  loadStudents(){
    this.userService.getAllUsersByRole('STUDENT')
      .subscribe(users => {
        this.students.set(users);
      });
  }

  togglePaid(fee: any) {
    this.feeService.toggleFee(fee.id).subscribe(updated => {
      fee.paid = updated.paid;
      fee.paymentDate = updated.paymentDate;

      this.fees.set([...this.fees()]);//to fix the render, signals auto refresh
    });
  }


  studentNameById = computed(() => {
    const map: Record<number, string> = {};

    this.students().forEach(s => {
      map[s.id] = `${s.firstName} ${s.lastName}`;
    });

    return map;
  });


}

