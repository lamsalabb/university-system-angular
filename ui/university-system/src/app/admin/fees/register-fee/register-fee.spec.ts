import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RegisterFee} from './register-fee';

describe('RegisterFee', () => {
  let component: RegisterFee;
  let fixture: ComponentFixture<RegisterFee>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterFee]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterFee);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
