import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookDetailsButtonComponent } from './book-details-button.component';

describe('BookDetailsButtonComponent', () => {
  let component: BookDetailsButtonComponent;
  let fixture: ComponentFixture<BookDetailsButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookDetailsButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookDetailsButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
