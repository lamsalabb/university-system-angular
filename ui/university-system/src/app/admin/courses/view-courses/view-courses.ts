import {Component, signal} from '@angular/core';
import {Course} from '../../../services/course';
import {FormsModule} from '@angular/forms';
import {User} from '../../../services/user';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-view-courses',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './view-courses.html',
  styleUrl: './view-courses.css',
})
export class ViewCourses {
courses = signal<any[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);
  instructors: any[] = [];

  constructor(private courseService: Course, private userService:User) {

  }

  ngOnInit() {
    this.loadCourses();
    this.loadInstructors();
  }


  loadCourses() {
    this.loading.set(true);

    this.courseService.getAllCourses().subscribe({
      next: (courses) => {
        this.courses.set(courses);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load courses');
        this.loading.set(false);
      },
    });
  }

  loadInstructors() {
    this.userService.getAllUsersByRole('INSTRUCTOR').subscribe(users => {
      this.instructors = users;
    });

  }

  deleteCourse(id: number) {
    if (!confirm('Delete this course?')) return;

    this.courseService.deleteCourse(id).subscribe(() => {
      this.courses.update(c => c.filter(x => x.id !== id));
    });
  }

  editingCourse = signal<any | null>(null);

  editForm = {
    title: '',
    code: '',
    credits: 0,
    description: '',
    instructorId: 0
  };


  openEdit(c: any) {
    this.editingCourse.set(c);

    this.editForm = {
      title: c.title,
      code: c.code,
      credits: c.credits,
      description: c.description,
      instructorId: c.instructorId
    };
  }


  closeEdit() {
    this.editingCourse.set(null);
  }

  saveEdit() {
    const payload = { ...this.editForm };//copying form data into another object

    this.courseService
      .updateCourse(this.editingCourse()!.id, payload)
      .subscribe(updated => {
        this.courses.update(list =>
          list.map(c => c.id === updated.id ? updated : c)
        );
        this.closeEdit();
      });
  }

  canSaveCourse(): boolean {
    return !!(
      this.editForm.title &&
      this.editForm.code &&
      this.editForm.credits &&
      this.editForm.instructorId
    );
  }

}
