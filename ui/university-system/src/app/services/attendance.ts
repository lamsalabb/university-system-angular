import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Attendance {
  private readonly apiUrl = `${environment.url}/api/attendances`;

  constructor(private http: HttpClient) {}

  markAttendance(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/mark`, request);
  }

  getByStudent(studentId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/student/${studentId}`);
  }

  getByCourse(courseId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/course/${courseId}`);
  }

  getByStudentAndCourse(studentId: number, courseId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/student/${studentId}/course/${courseId}`);
  }

  updateAttendance(attendanceId: number, status: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/student/${attendanceId}/attendance/${status}`, null);
  }

  getSummary(studentId: number, courseId: number): Observable<any> {
    return this.http.get<any>(
      `${this.apiUrl}/student/${studentId}/course/${courseId}/summary`
    );
  }


}
