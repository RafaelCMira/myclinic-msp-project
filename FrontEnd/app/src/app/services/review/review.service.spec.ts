import { TestBed } from '@angular/core/testing';

import { ReviewDoctorService } from './review.service';

describe('ReviewDoctorService', () => {
  let service: ReviewDoctorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReviewDoctorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
