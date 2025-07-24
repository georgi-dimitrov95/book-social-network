import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowBookIconComponent } from './borrow-book-icon.component';

describe('BorrowBookIconComponent', () => {
  let component: BorrowBookIconComponent;
  let fixture: ComponentFixture<BorrowBookIconComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BorrowBookIconComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowBookIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
