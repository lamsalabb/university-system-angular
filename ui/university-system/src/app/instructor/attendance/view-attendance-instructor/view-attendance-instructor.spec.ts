import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewAttendanceInstructor} from './view-attendance-instructor';

describe('ViewAttendanceInstructor', () => {
  let component: ViewAttendanceInstructor;
  let fixture: ComponentFixture<ViewAttendanceInstructor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewAttendanceInstructor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAttendanceInstructor);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
