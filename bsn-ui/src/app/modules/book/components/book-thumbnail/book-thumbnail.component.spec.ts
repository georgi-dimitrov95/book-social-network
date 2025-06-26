import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookThumbnailComponent } from './book-thumbnail.component';

describe('BookThumbnailComponent', () => {
  let component: BookThumbnailComponent;
  let fixture: ComponentFixture<BookThumbnailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookThumbnailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookThumbnailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
