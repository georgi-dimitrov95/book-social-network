import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorBooksCarouselComponent } from './author-books-carousel.component';

describe('AuthorBooksCarouselComponent', () => {
  let component: AuthorBooksCarouselComponent;
  let fixture: ComponentFixture<AuthorBooksCarouselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AuthorBooksCarouselComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorBooksCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
