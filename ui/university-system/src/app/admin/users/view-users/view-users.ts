import {Component, signal} from '@angular/core';
import {User} from '../../../services/user';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-view-users',
  imports: [
    RouterLink,
  ],
  templateUrl: './view-users.html',
  styleUrl: './view-users.css',
})
export class ViewUsers {
  users = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private userService: User) {
    this.loadUsers();
  }

  loadUsers() {
    this.loading.set(true);

    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users.set(users);
        this.loading.set(false);
      },
      error: () => {
        console.log(this.error);
        this.error.set('Failed to load users');
        this.loading.set(false);
      },
    });
  }

  deleteUser(id: number) {
    if (!confirm('Delete this user?')) return;

    this.userService.deleteUser(id).subscribe(() => {
      this.users.update(u => u.filter(x => x.id !== id));
    });
  }


}
