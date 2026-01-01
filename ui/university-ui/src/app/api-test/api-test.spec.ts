import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApiTestComponent } from './api-test';

describe('ApiTest', () => {
  let component: ApiTestComponent;
  let fixture: ComponentFixture<ApiTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApiTestComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApiTestComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
