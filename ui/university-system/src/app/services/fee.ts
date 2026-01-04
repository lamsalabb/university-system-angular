import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Fee {

  private readonly apiUrl = `${environment.url}/api/fees`;

  constructor(private http: HttpClient) {}

  getFeesByStudent(studentId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/student/${studentId}`);
  }

    getAllFees(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  getFeeById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createFee(request: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, request);
  }

  toggleFee(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/toggle`, {});
  }



  getFeeSummary(studentId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/student/${studentId}/summary`);
  }
}
