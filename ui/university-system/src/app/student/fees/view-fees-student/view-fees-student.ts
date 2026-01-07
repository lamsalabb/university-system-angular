import {Component, signal} from '@angular/core';
import {Fee} from '../../../services/fee';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-view-fees-student',
    imports: [

    ],
  templateUrl: './view-fees-student.html',
  styleUrl: './view-fees-student.css',
})
export class ViewFeesStudent {

  id : number;

  fees = signal<any[]>([]);
  outstanding = signal(0);
  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private feeService:Fee, private authService: AuthService) {
    this.id = this.authService.userId;

  }

  ngOnInit() {
    this.loadFees();

    this.feeService.getFeeSummary(this.id).subscribe({
      next: res => {
        this.outstanding.set(res.outstandingAmount);
      },
      error: () => {
        this.error.set('Failed to load fee summary');
      }
    });
  }


  loadFees(){

    this.feeService.getFeesByStudent(this.id).subscribe({
      next: fees=>{
          this.fees.set(fees);
          this.loading.set(false);
      },
      error: err=>{
        this.error.set('Failed to load enrollments');
        this.loading.set(false);
      }
    })
  }




}
