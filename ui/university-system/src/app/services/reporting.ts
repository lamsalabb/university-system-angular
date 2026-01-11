import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class Reporting {
  private readonly apiUrl = `${environment.url}/api/reports`;

  constructor(private http: HttpClient) {
  }


  getCourseEnrollment(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/course-enrollment`);
  }

  getPdf() {
    return this.http.get(`${this.apiUrl}/average-grades`, {
      responseType: "blob"
    });
  }
}
