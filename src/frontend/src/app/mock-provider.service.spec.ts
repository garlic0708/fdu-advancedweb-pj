import { TestBed, inject } from '@angular/core/testing';

import { MockProviderService } from './mock-provider.service';

describe('MockProviderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MockProviderService]
    });
  });

  it('should be created', inject([MockProviderService], (service: MockProviderService) => {
    expect(service).toBeTruthy();
  }));
});
