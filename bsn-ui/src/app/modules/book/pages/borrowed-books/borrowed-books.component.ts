import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BorrowedBookResponse} from '../../../../services/models/borrowed-book-response';
import {FeedbackService} from '../../../../services/services/feedback.service';
import {BookService} from '../../../../services/services/book.service';
import {BookResponse} from '../../../../services/models/book-response';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {Router} from '@angular/router';

enum BookFilter {
  ALL = 'ALL',
  CURRENTLY = 'CURRENTLY',
  RETURNED = 'RETURNED'
}

@Component({
  selector: 'app-borrowed-books',
  standalone: false,
  templateUrl: './borrowed-books.component.html',
  styleUrl: './borrowed-books.component.scss'
})
export class BorrowedBooksComponent implements OnInit {

  page = 0;
  size = 2;
  pages: any = [];
  borrowedBooks: PageResponseBorrowedBookResponse = {};
  selectedBook: BookResponse | undefined = undefined;
  feedbackRequest: FeedbackRequest = {bookId: 0, comment: '', rating: 0};
  currentFilter: BookFilter = BookFilter.ALL;

  constructor(
    private bookService: BookService,
    private feedbackService: FeedbackService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchBooks();
  }

  protected fetchBooks(filter?: BookFilter) {
    let observable;
    const params = {page: this.page, size: this.size};
    this.currentFilter = filter ?? this.currentFilter;

    switch (this.currentFilter) {
      case BookFilter.RETURNED:
        observable = this.bookService.findAllReturnedBooksByUser(params);
        break;
      case BookFilter.CURRENTLY:
        observable = this.bookService.findAllCurrentlyBorrowedBooksByUser(params);
        break;
      default:
        observable = this.bookService.findAllBorrowedBooksByUser(params);
    }

    observable.subscribe({
      next: (response) => {
        this.borrowedBooks = response;
        this.pages = Array(this.borrowedBooks.totalPages).fill(0).map((x, i) => i);
      }
    })
  }

  selectBook(book: BorrowedBookResponse) {
    this.selectedBook = book;
    this.feedbackRequest.bookId = book.id as number;
  }

  returnBook(withFeedback: boolean) {
    this.bookService.returnBook({
      'bookId': this.selectedBook?.id as number
    }).subscribe({
      next: () => {
        if (withFeedback) {
          this.giveFeedback();
        }
        this.selectedBook = undefined;
        this.fetchBooks();
      }
    });
  }

  private giveFeedback() {
    this.feedbackService.saveFeedback({
      body: this.feedbackRequest
    }).subscribe({next: () => {}});
  }

  onPageChange(page: number) {
    this.page = page;
    this.fetchBooks();
  }

  goToBookDetails(bookId: number | undefined) {
    this.router.navigate(['books', 'book-details'], {queryParams: {bookId}});
  }

  protected readonly BookFilter = BookFilter;
}
