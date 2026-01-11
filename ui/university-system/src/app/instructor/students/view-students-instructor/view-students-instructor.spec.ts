import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewStudentsInstructor} from './view-students-instructor';

describe('ViewStudentsInstructor', () => {
  let component: ViewStudentsInstructor;
  let fixture: ComponentFixture<ViewStudentsInstructor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewStudentsInstructor]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ViewStudentsInstructor);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
