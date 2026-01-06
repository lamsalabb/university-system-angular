import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAttendanceStudent } from './view-attendance-student';

describe('ViewAttendanceStudent', () => {
  let component: ViewAttendanceStudent;
  let fixture: ComponentFixture<ViewAttendanceStudent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewAttendanceStudent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAttendanceStudent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
