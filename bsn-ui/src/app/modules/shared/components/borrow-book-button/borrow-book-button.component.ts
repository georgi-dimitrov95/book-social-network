import {Component, Input, OnInit} from '@angular/core';
import {BookService} from '../../../../services/services/book.service';
import {BookContextService} from '../../../../services/context/book-context.service';

@Component({
  selector: 'app-borrow-book-button',
  standalone: false,
  templateUrl: './borrow-book-button.component.html',
  styleUrl: './borrow-book-button.component.scss'
})
export class BorrowBookButtonComponent implements OnInit {

  @Input() bookId: any;

  ngOnInit() {
    if (!this.bookId) {
      this.bookId = this.bookContext.getBookId();
    }
  }

  constructor(
    private bookService: BookService,
    private bookContext: BookContextService
  ) {}

  borrowBook() {
    if (!this.bookId) {
      console.error('no bookId');
      return;
    }

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
