import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class User {

  private readonly apiUrl = `${environment.url}` + '/api/users';

  constructor(private http: HttpClient) {
  }


  getAllUsers(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  getAllUsersByRole(role: any): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/by-role`, {params: {role}}
    )
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createUser(request: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, request);
  }

  updateUser(id: number, request: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, request);
  }

  changePasswordUser(id: number, request: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/password`, request);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
