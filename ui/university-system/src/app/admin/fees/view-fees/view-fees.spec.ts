import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewFees} from './view-fees';

describe('ViewFees', () => {
  let component: ViewFees;
  let fixture: ComponentFixture<ViewFees>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewFees]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ViewFees);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
