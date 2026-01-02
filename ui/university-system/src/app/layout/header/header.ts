import {Component, signal} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';
import {FormGroup} from '@angular/forms';
import {AuthService} from '../../modules/auth/services/auth.service';

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
