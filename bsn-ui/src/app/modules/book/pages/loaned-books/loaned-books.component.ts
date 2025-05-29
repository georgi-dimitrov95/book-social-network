import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BookService} from '../../../../services/services/book.service';
import {Router} from '@angular/router';

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

  constructor(
    private bookService: BookService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.findAllLoanedBooks();
  }

  private findAllLoanedBooks() {
    this.bookService.findAllLoanedBooksByUser({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response) => {
        this.loanedBooks = response;
        this.pages = Array(this.loanedBooks.totalPages)
          .fill(0)
          .map((x, i) => i);
      }
    });
  }

  displayLoanedAndReturnedBooks() {
    this.bookService.findAllLoanedAndReturnedBooksByUser({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response) => {
        this.loanedBooks = response;
        this.pages = Array(this.loanedBooks.totalPages)
          .fill(0)
          .map((x, i) => i);
      }
    });
  }

  displayCurrentlyBorrowedBooks() {
    this.bookService.findAllCurrentlyLoanedBooksByUser({
      page: this.page,
      size: this.size
      }).subscribe({
       next: (response) => {
         this.loanedBooks = response;
         this.pages = Array(this.loanedBooks.totalPages)
           .fill(0)
           .map((x, i) => i);
       }
    });
    console.log(this.loanedBooks);
  }

  onPageChange(page: number) {
    this.page = page;
    this.findAllLoanedBooks();
  }

  goToBookDetails(bookId: number | undefined) {
    this.router.navigate(['books', 'book-details'], {queryParams: {bookId}});
  }
}
