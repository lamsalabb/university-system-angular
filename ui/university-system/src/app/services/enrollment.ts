import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Enrollment {
  private readonly apiUrl = `${environment.url}/api/enrollments`;

  constructor(private http: HttpClient) {}

  enroll(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/enroll`, request);
  }

  drop(id: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/drop`, {});
  }

  getEnrollmentByStudent(studentId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/student/${studentId}`);
  }

  getEnrollmentByCourse(courseId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/course/${courseId}`);
  }

  getAllEnrollments(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }


}
