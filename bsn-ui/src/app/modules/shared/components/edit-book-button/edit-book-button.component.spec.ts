import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditBookButtonComponent } from './edit-book-button.component';

describe('EditBookButtonComponent', () => {
  let component: EditBookButtonComponent;
  let fixture: ComponentFixture<EditBookButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditBookButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditBookButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
