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

  // GET /api/users
  // getAllUsers(): Observable<any[]> {
  //   return this.http.get<any[]>(this.apiUrl);
  // }
  // GET /api/users
  getAllUsers(page:number, size:number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  // GET /api/users
  getAllUsersByRole(role: any): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/by-role`, { params: { role } }
    )
  }


  // GET /api/users/{id}
  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // POST /api/users
  createUser(request: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, request);
  }

  // PUT /api/users/{id}
  updateUser(id: number, request: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, request);
  }

  // PUT /api/users/{id}
  changePasswordUser(id: number, request: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/password`, request);
  }


  // DELETE /api/users/{id}
  deleteUser(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
