import { TestBed } from '@angular/core/testing';

import { ApiStateService } from './api-state';

describe('ApiState', () => {
  let service: ApiStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
