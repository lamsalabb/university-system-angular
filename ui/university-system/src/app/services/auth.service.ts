import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private url = `${environment.url}`;

  constructor(
    private http:HttpClient,
    private router:Router
  ) {
  }

  login(credentials:any){
    return this.http.post<any>(`${this.url}/api/auth/login`, credentials);
  }

  handleLoginSuccess(response:any){
    localStorage.setItem('token',response.token);
    localStorage.setItem('role',response.role);

    if(response.role == 'ADMIN'){
      this.router.navigate(['/admin/dashboard']);
    }
    else if(response.role == 'INSTRUCTOR'){
      this.router.navigate(['/instructor/dashboard'])
    }
    else {
      this.router.navigate(['/student/dashboard'])
    }
  }

  logout(){
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
