import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowBookButtonComponent } from './borrow-book-button.component';

describe('BorrowBookButtonComponent', () => {
  let component: BorrowBookButtonComponent;
  let fixture: ComponentFixture<BorrowBookButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BorrowBookButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowBookButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
