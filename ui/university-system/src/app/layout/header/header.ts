import {Component, signal} from '@angular/core';
import {RouterLink} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, NgOptimizedImage],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {

  constructor(private authService: AuthService,) {}

  private role = localStorage.getItem('role')?.toLowerCase();

  link = signal("/"+this.role+"/dashboard");

  logout(){
    this.authService.logout();
  }

}
