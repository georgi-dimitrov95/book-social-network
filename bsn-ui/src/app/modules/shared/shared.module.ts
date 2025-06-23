import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedRoutingModule } from './shared-routing.module';
import { PageNavigationComponent } from './components/page-navigation/page-navigation.component';
import { BorrowBookButtonComponent } from './components/borrow-book-button/borrow-book-button.component';
import { FavoriteButtonComponent } from './components/favorite-button/favorite-button.component';


@NgModule({
  declarations: [
    PageNavigationComponent,
    BorrowBookButtonComponent,
    FavoriteButtonComponent
  ],
  imports: [
    CommonModule,
    SharedRoutingModule
  ],
    exports: [
        PageNavigationComponent,
        BorrowBookButtonComponent,
        FavoriteButtonComponent
    ]
})
export class SharedModule { }
