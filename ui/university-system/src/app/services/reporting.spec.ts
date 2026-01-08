import { TestBed } from '@angular/core/testing';

import { Reporting } from './reporting';

describe('Reporting', () => {
  let service: Reporting;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Reporting);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
