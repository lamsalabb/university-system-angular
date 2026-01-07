import {Component, computed, input, output} from '@angular/core';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pagination.html',
  styleUrl: './pagination.css',
})
export class Pagination {
  page = input(0);
  size = input(10);
  total = input(0);
  loading = input(false);

  pageChange = output<number>();
  sizeChange = output<number>();

  totalPages = computed(() =>
    Math.ceil(this.total() / this.size())
  );

  isFirst = computed(() => this.page() === 0);
  isLast = computed(() => this.page() + 1 >= this.totalPages());

  next() {
    if (this.isLast()) return;
    this.pageChange.emit(this.page() + 1);
  }

  prev() {
    if (this.isFirst()) return;
    this.pageChange.emit(this.page() - 1);
  }

  changeSize(event: Event) {
    const value = Number((event.target as HTMLSelectElement).value);
    this.sizeChange.emit(value);
  }
}
