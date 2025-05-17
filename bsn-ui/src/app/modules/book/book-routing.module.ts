import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './pages/main/main.component';
import { BookListComponent } from './pages/book-list/book-list.component';
import { MyBooksComponent } from './pages/my-books/my-books.component';
import {ManageBookComponent} from './pages/manage-book/manage-book.component';
import {BorrowedBooksComponent} from './pages/borrowed-books/borrowed-books.component';
import {LoanedBooksComponent} from './pages/loaned-books/loaned-books.component';

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
        path: 'my-borrowed-books',
        component: BorrowedBooksComponent
      },
      {
        path: 'loaned-books',
        component: LoanedBooksComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
