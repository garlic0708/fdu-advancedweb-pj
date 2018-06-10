import { TestBed, inject, async } from '@angular/core/testing';

import { CurrentUserService } from './current-user.service';

fdescribe('CurrentUserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CurrentUserService]
    });
  });

  it('should be created', inject([CurrentUserService], (service: CurrentUserService) => {
    expect(service).toBeTruthy();
  }));

  it('should return correct user when login', async(
    inject([CurrentUserService], (service: CurrentUserService) => {
      service.login('a@best.com', '1')
        .subscribe(user => expect(user.role).toBe('TEACHER'))
    })
  ))
});
