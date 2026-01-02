import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Header} from '../header/header';
import {Footer} from '../footer/footer';

@Component({
  selector: 'app-layout',
  imports: [Header, Footer, RouterOutlet],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class Layout {

}
