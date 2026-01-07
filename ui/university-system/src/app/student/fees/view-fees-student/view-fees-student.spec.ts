import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewFeesStudent} from './view-fees-student';

describe('ViewFeesStudent', () => {
  let component: ViewFeesStudent;
  let fixture: ComponentFixture<ViewFeesStudent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewFeesStudent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewFeesStudent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
