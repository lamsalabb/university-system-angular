import {Component, signal} from '@angular/core';
import {User} from '../../../services/user';
import {AuthService} from '../../../services/auth.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-view-profile-student',
  imports: [
    FormsModule
  ],
  templateUrl: './view-profile-student.html',
  styleUrl: './view-profile-student.css',
})
export class ViewProfileStudent {

  student = signal<any | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);
  id!: number;

  showChangePassword = signal(false);
  currentPassword = '';
  newPassword = '';
  confirmPassword = '';
  passwordError = signal<string | null>(null);


  constructor(private userService: User, private authService: AuthService) {
  }

  ngOnInit() {
    this.id = this.authService.userId;
    this.loadProfile();
  }

  loadProfile() {
    this.userService.getUserById(this.id).subscribe({
      next: data => {
        this.student.set(data);
        this.loading.set(false);

      },
      error: error => {
        this.error.set('Failed to load profile');
        this.loading.set(false);
      }
    })
  }

  submitPasswordChange() {

    this.passwordError.set(null);

    if (this.newPassword !== this.confirmPassword) {
      this.passwordError.set('Passwords do not match');
      return;
    }

    this.userService.changePasswordUser(this.id, {
      currentPassword: this.currentPassword,
      newPassword: this.newPassword
    }).subscribe({
      next: () => {
        this.showChangePassword.set(false);
        this.currentPassword = '';
        this.newPassword = '';
        this.confirmPassword = '';
        alert("Password changed successfully!");
      },
      error: err => {
        if (err.status === 401) {
          this.passwordError.set('Current password is incorrect');
        } else {
          this.passwordError.set('Failed to change password');
        }
      }
    });
  }

}


