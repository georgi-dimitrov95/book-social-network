import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BookService} from '../../../../services/services/book.service';
import {Router} from '@angular/router';

enum BookFilter {
  ALL = 'ALL',
  CURRENTLY = 'CURRENTLY',
  RETURNED = 'RETURNED'
}

@Component({
  selector: 'app-loaned-books',
  standalone: false,
  templateUrl: './loaned-books.component.html',
  styleUrl: './loaned-books.component.scss'
})
export class LoanedBooksComponent implements OnInit{

  page = 0;
  size = 5;
  pages: any = [];
  loanedBooks: PageResponseBorrowedBookResponse = {};
  currentFilter: BookFilter = BookFilter.ALL;

  constructor(
    private bookService: BookService,
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
        observable = this.bookService.findAllLoanedAndReturnedBooksByUser(params);
        break;
      case BookFilter.CURRENTLY:
        observable = this.bookService.findAllCurrentlyLoanedBooksByUser(params);
        break;
      default:
        observable = this.bookService.findAllLoanedBooksByUser(params);
    }

    observable.subscribe({
      next: (response) => {
        this.loanedBooks = response;
        this.pages = Array(this.loanedBooks.totalPages).fill(0).map((x, i) => i);
      }
    })
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
