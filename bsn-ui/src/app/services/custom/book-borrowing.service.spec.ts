import { TestBed } from '@angular/core/testing';

import {BookBorrowingService} from './book-borrowing.service';

describe('BookBorrowingService', () => {
  let service: BookBorrowingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookBorrowingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
