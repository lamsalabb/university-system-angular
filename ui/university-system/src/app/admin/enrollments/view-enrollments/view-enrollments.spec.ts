import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEnrollments } from './view-enrollments';

describe('ViewEnrollments', () => {
  let component: ViewEnrollments;
  let fixture: ComponentFixture<ViewEnrollments>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewEnrollments]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewEnrollments);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
