import {ComponentFixture, TestBed} from '@angular/core/testing';

import {MarkAttendanceInstructor} from './mark-attendance-instructor';

describe('MarkAttendanceInstructor', () => {
  let component: MarkAttendanceInstructor;
  let fixture: ComponentFixture<MarkAttendanceInstructor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MarkAttendanceInstructor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MarkAttendanceInstructor);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
