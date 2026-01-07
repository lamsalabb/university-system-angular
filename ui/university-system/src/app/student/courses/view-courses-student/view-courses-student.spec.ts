import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewCoursesStudent} from './view-courses-student';

describe('ViewCoursesStudent', () => {
  let component: ViewCoursesStudent;
  let fixture: ComponentFixture<ViewCoursesStudent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewCoursesStudent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewCoursesStudent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
