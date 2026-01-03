import {Component, signal} from '@angular/core';
import {User} from '../../../services/user';
import {RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-view-users',
  imports: [
    RouterLink,
    FormsModule,
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

  editingUser = signal<any | null>(null)

  editForm = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    isActive: true
  }

  openEdit(u: any) {
    this.editingUser.set(u)
    this.editForm = {
      firstName: u.firstName,
      lastName: u.lastName,
      email: u.email,
      password: '',
      isActive: u.active
    }
  }

  closeEdit() {
    this.editingUser.set(null)
  }

  saveEdit() {
    const payload: any = { ...this.editForm }

    if (!payload.password) delete payload.password

    this.userService.updateUser(this.editingUser()!.id, payload)
      .subscribe(updated => {
        this.users.update(list =>
          list.map(u => u.id === updated.id ? updated : u)
        )
        this.closeEdit()
      })
  }




}
