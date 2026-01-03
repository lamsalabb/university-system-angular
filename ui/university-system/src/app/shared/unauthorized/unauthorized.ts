import {Component} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-unauthorized',
  standalone: true,
  imports: [],
  templateUrl: './unauthorized.html',
  styleUrl: './unauthorized.css',
})

export class Unauthorized {

  role = localStorage.getItem('role');

  constructor(private router: Router) {
  }

  goToDashboard() {
    if (this.role === 'ADMIN') {
      this.router.navigate(['/admin/dashboard']);
    } else if (this.role === 'STUDENT') {
      this.router.navigate(['/student/dashboard']);
    } else if (this.role === 'INSTRUCTOR') {
      this.router.navigate(['/instructor/dashboard']);
    } else {
      this.router.navigate(['/login']);
    }
  }

}
