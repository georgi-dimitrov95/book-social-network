import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageBookComponent } from './manage-book.component';

describe('CreateBookComponent', () => {
  let component: ManageBookComponent;
  let fixture: ComponentFixture<ManageBookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManageBookComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
