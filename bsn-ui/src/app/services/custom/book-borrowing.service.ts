import { Injectable } from '@angular/core';
import {BookService} from '../services/book.service';

@Injectable({
  providedIn: 'root'
})
export class BookBorrowingService {

  constructor(
    private bookService: BookService
  ) {}

  borrowBook(bookId: number) {
    this.bookService.borrowBook({ bookId }).subscribe({
      next: () => {console.log('GREAT SUCCESS')},
      error: (err) => {console.log(err);}
    });
  }

  borrowBookByTitleFromUser(title: string, userId: number) {
    this.bookService.borrowBookByTitleFromUser({ title, userId }).subscribe({
      next: () => {console.log('GREAT SUCCESS')},
      error: (err) => {console.log(err);}
    })
  }
}
