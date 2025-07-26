import {Component, Input, OnInit} from '@angular/core';
import {BookContextService} from '../../../../services/context/book-context.service';
import {BookBorrowingService} from '../../../../services/custom/book-borrowing.service';

@Component({
  selector: 'app-borrow-book-icon',
  standalone: false,
  templateUrl: './borrow-book-icon.component.html',
  styleUrl: './borrow-book-icon.component.scss'
})
export class BorrowBookIconComponent implements OnInit {

  @Input() bookId: any;

  constructor(
    private bookBorrowingService: BookBorrowingService,
    private bookContext: BookContextService
  ) {}

  ngOnInit() {
    if (!this.bookId) {
      this.bookContext.bookId$.subscribe(bookId => {
        this.bookId = bookId;
      })
    }
  }

  borrowBook() {
    if (!this.bookId) {
      console.error('no bookId');
      return;
    }
    this.bookBorrowingService.borrowBook(this.bookId);
  }
}
