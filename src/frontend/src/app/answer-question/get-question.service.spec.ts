import { TestBed, inject } from '@angular/core/testing';

import { GetQuesstionService } from './get-question.service';

describe('GetQuesstionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GetQuesstionService]
    });
  });

  it('should be created', inject([GetQuesstionService], (service: GetQuesstionService) => {
    expect(service).toBeTruthy();
  }));
});