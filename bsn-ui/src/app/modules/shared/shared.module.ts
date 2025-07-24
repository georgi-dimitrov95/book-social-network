import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { PageNavigationComponent } from './components/page-navigation/page-navigation.component';
import { BorrowBookButtonComponent } from './components/borrow-book-button/borrow-book-button.component';
import { FavoriteButtonComponent } from './components/favorite-button/favorite-button.component';
import { BookDetailsButtonComponent } from './components/book-details-button/book-details-button.component';
import { EditBookButtonComponent } from './components/edit-book-button/edit-book-button.component';
import { ShareBookButtonComponent } from './components/share-book-button/share-book-button.component';
import { BorrowBookIconComponent } from './components/borrow-book-icon/borrow-book-icon.component';


@NgModule({
  declarations: [
    PageNavigationComponent,
    BorrowBookButtonComponent,
    FavoriteButtonComponent,
    BookDetailsButtonComponent,
    EditBookButtonComponent,
    ShareBookButtonComponent,
    BorrowBookIconComponent
  ],
  imports: [
    CommonModule,
    SharedRoutingModule
  ],
  exports: [
    PageNavigationComponent,
    BorrowBookButtonComponent,
    FavoriteButtonComponent,
    BookDetailsButtonComponent,
    EditBookButtonComponent,
    ShareBookButtonComponent
  ]
})
export class SharedModule { }
