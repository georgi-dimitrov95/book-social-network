import {Component, Input, OnInit} from '@angular/core';
import {BookBorrowingService} from '../../../../services/custom/book-borrowing.service';
import {BookContextService} from '../../../../services/context/book-context.service';

@Component({
  selector: 'app-borrow-book-button',
  standalone: false,
  templateUrl: './borrow-book-button.component.html',
  styleUrl: './borrow-book-button.component.scss'
})
export class BorrowBookButtonComponent implements OnInit {

  @Input() bookTitle: string | null = null;
  @Input() userId: number = 0;

  ngOnInit() {
    if (!this.bookTitle) {
      this.bookContextService.bookTitle$.subscribe(bookTitle => {
        this.bookTitle = bookTitle;
      })
    }
  }

  constructor(
    private bookBorrowingService: BookBorrowingService,
    private bookContextService: BookContextService
  ) {}

  borrowBookByTitleFromUser() {
    this.bookBorrowingService.borrowBookByTitleFromUser(this.bookTitle!, this.userId);
  }
}
