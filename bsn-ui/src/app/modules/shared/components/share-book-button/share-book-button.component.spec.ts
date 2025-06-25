import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareBookButtonComponent } from './share-book-button.component';

describe('ShareBookButtonComponent', () => {
  let component: ShareBookButtonComponent;
  let fixture: ComponentFixture<ShareBookButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShareBookButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShareBookButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
