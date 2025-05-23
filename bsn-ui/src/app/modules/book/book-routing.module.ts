import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './pages/main/main.component';
import { BookListComponent } from './pages/book-list/book-list.component';
import { MyBooksComponent } from './pages/my-books/my-books.component';
import {ManageBookComponent} from './pages/manage-book/manage-book.component';
import {BorrowedBooksComponent} from './pages/borrowed-books/borrowed-books.component';
import {LoanedBooksComponent} from './pages/loaned-books/loaned-books.component';
import {ReturnedBooksComponent} from './pages/returned-books/returned-books.component';
import {WishlistComponent} from './pages/wishlist/wishlist.component';
import {BookDetailsComponent} from './pages/book-details/book-details.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: '',
        component: BookListComponent
      },
      {
        path: 'book-details',
        component: BookDetailsComponent
      },
      {
        path: 'my-books',
        component: MyBooksComponent
      },
      {
        path: 'manage-book',
        component: ManageBookComponent
      },
      {
        path: 'manage-book/:bookId',
        component: ManageBookComponent
      },
      {
        path: 'borrowed-books',
        component: BorrowedBooksComponent
      },
      {
        path: 'loaned-books',
        component: LoanedBooksComponent
      },
      {
        path: 'returned-books',
        component: ReturnedBooksComponent
      },
      {
        path: 'wishlist',
        component: WishlistComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
