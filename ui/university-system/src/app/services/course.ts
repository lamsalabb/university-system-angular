import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class Course {

  private readonly apiUrl = `${environment.url}/api/courses`;

  constructor(private http: HttpClient) {}

  getAllCourses(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getCourseById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createCourse(request: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, request);
  }

  updateCourse(id: number, request: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, request);
  }

  deleteCourse(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}
