import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiStateService } from '../api-state';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-api-test',
  imports: [
    JsonPipe
  ],
  templateUrl: './api-test.html'
})
export class ApiTestComponent implements OnInit {

  constructor(
    private http: HttpClient,
    public state: ApiStateService
  ) {}

  ngOnInit() {
    if (!this.state.data) {
      this.http.get('http://localhost:8080/api/users/1')
        .subscribe({
          next: res => this.state.data = res,
          error: err => this.state.data = { error: err.message }
        });
    }
  }
}
