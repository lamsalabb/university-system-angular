import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewCoursesInstructor} from './view-courses-instructor';

describe('ViewCoursesInstructor', () => {
  let component: ViewCoursesInstructor;
  let fixture: ComponentFixture<ViewCoursesInstructor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewCoursesInstructor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewCoursesInstructor);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
