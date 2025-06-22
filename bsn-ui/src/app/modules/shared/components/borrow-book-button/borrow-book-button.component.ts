import {Component, Input} from '@angular/core';
import {BookService} from '../../../../services/services/book.service';

@Component({
  selector: 'app-borrow-book-button',
  standalone: false,
  templateUrl: './borrow-book-button.component.html',
  styleUrl: './borrow-book-button.component.scss'
})
export class BorrowBookButtonComponent {

  @Input() bookId: any;

  constructor(
    private bookService: BookService
  ) {}

  borrowBook() {
    this.bookService.borrowBook({'bookId': this.bookId}).subscribe({
      next: () => {
        console.log('GREAT SUCCESS')
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
}
