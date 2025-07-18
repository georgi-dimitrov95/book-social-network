import { TestBed } from '@angular/core/testing';

import { BookContextService } from './book-context.service';

describe('BookContextService', () => {
  let service: BookContextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookContextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
