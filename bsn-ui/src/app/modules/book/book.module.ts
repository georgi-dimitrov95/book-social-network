import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { MainComponent } from './pages/main/main.component';
import { MenuComponent } from './components/menu/menu.component';
import { BookListComponent } from './pages/book-list/book-list.component';
import { BookCardComponent } from './components/book-card/book-card.component';
import { RatingComponent } from './components/rating/rating.component';
import { MyBooksComponent } from './pages/my-books/my-books.component';
import { ManageBookComponent } from './pages/manage-book/manage-book.component';
import {FormsModule} from '@angular/forms';
import { BorrowedBooksComponent } from './pages/borrowed-books/borrowed-books.component';
import { LoanedBooksComponent } from './pages/loaned-books/loaned-books.component';
import { ReturnedBooksComponent } from './pages/returned-books/returned-books.component';
import { WishlistComponent } from './pages/wishlist/wishlist.component';
import { BookDetailsComponent } from './pages/book-details/book-details.component';
import {SharedModule} from '../shared/shared.module';
import { BookThumbnailComponent } from './components/book-thumbnail/book-thumbnail.component';
import { AuthorBooksCarouselComponent } from './components/author-books-carousel/author-books-carousel.component';
import {UserModule} from '../user/user.module';


@NgModule({
  declarations: [
    MainComponent,
    MenuComponent,
    BookListComponent,
    BookCardComponent,
    RatingComponent,
    MyBooksComponent,
    ManageBookComponent,
    BorrowedBooksComponent,
    LoanedBooksComponent,
    ReturnedBooksComponent,
    WishlistComponent,
    BookDetailsComponent,
    BookThumbnailComponent,
    AuthorBooksCarouselComponent
  ],
  imports: [
    CommonModule,
    BookRoutingModule,
    FormsModule,
    SharedModule,
    UserModule
  ]
})
export class BookModule { }
