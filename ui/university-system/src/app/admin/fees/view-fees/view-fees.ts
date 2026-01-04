import {Component, computed, effect, OnInit, signal} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Fee} from '../../../services/fee';
import {User} from '../../../services/user';
import {ReactiveFormsModule} from '@angular/forms';
import {Pagination} from '../../../shared/pagination/pagination';

@Component({
  selector: 'app-view-fees',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    Pagination
  ],
  templateUrl: './view-fees.html',
  styleUrl: './view-fees.css',
})
export class ViewFees implements OnInit {
  fees = signal<any[]>([]);
  students = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);
  currentPage = signal(0);
  pageSize = signal(10);
  totalElements = signal(0);

  constructor(private feeService:Fee, private userService:User) {
    effect(() => {
      this.currentPage();
      this.pageSize();
      this.loadFees();
    });
  }

  ngOnInit() {
    this.loadFees();
    this.loadStudents();
  }

  loadFees(){
    this.loading.set(true);

    this.feeService.getAllFees(this.currentPage(),this.pageSize()).subscribe({
      next: fees => {
        this.fees.set(fees.content);
        this.totalElements.set(fees.totalElements);
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

