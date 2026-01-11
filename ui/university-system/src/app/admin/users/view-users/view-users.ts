import {Component, effect, signal} from '@angular/core';
import {User} from '../../../services/user';
import {RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {Pagination} from '../../../shared/pagination/pagination';

@Component({
  selector: 'app-view-users',
  imports: [
    RouterLink,
    FormsModule,
    Pagination,
  ],
  templateUrl: './view-users.html',
  styleUrl: './view-users.css',
})
export class ViewUsers {
  users = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  currentPage = signal(0);
  pageSize = signal(10);
  totalElements = signal(0);
  editingUser = signal<any | null>(null)
  editForm = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    isActive: true
  }

  constructor(private userService: User) {
    effect(() => {
      this.currentPage();
      this.pageSize();
      this.loadUsers();
    });
  }

  loadUsers() {
    this.loading.set(false);
    this.userService.getAllUsers(this.currentPage(), this.pageSize()).subscribe({
      next: (users) => {
        this.users.set(users.content);
        this.totalElements.set(users.totalElements);
        this.loading.set(false);
      },
      error: () => {
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

  openEdit(u: any) {
    this.editingUser.set(u);
    this.editForm = {
      firstName: u.firstName,
      lastName: u.lastName,
      email: u.email,
      password: '',
      isActive: u.active
    }
  }

  closeEdit() {
    this.editingUser.set(null);
  }

  saveEdit() {
    const payload: any = {...this.editForm}

    if (!payload.password) delete payload.password

    this.userService.updateUser(this.editingUser()!.id, payload)
      .subscribe(updated => {
        this.users.update(list =>
          list.map(u => u.id === updated.id ? updated : u)
        )
        this.closeEdit()
      })
  }

  canSaveUser(): boolean {//try refactor
    return !!(
      this.editForm.firstName &&
      this.editForm.lastName &&
      this.editForm.email
    );
  }


}
