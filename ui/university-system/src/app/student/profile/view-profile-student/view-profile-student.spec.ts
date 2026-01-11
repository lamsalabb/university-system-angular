import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewProfileStudent} from './view-profile-student';

describe('ViewProfileStudent', () => {
  let component: ViewProfileStudent;
  let fixture: ComponentFixture<ViewProfileStudent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewProfileStudent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ViewProfileStudent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
