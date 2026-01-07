import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewProfileInstructor} from './view-profile-instructor';

describe('ViewProfileInstructor', () => {
  let component: ViewProfileInstructor;
  let fixture: ComponentFixture<ViewProfileInstructor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewProfileInstructor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewProfileInstructor);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
