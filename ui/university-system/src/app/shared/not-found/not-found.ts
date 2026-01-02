import {Component, signal} from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-not-found',
  imports: [],
  templateUrl: './not-found.html',
  styleUrl: './not-found.css',
})
export class NotFound {
  title = signal('404');
  message = signal('The page you are looking for does not exist.');

  constructor(private location: Location) {}

  goBack() {
    this.location.back();
  }
}
