import { Component } from '@angular/core';
import {LoginHeader} from '../login-header/login-header';
import {RouterOutlet} from '@angular/router';
import {Footer} from '../footer/footer';

@Component({
  selector: 'app-public-layout',
  imports: [LoginHeader, RouterOutlet, Footer],
  templateUrl: './public-layout.html',
  styleUrl: './public-layout.css',
})
export class PublicLayout {

}
