import {Component, OnInit} from '@angular/core';
import {PageResponseBorrowedBookResponse} from '../../../../services/models/page-response-borrowed-book-response';
import {BookService} from '../../../../services/services/book.service';

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
  ) {}

  ngOnInit(): void {
    this.findAllLoanedBooks();
  }

  private findAllLoanedBooks() {
    this.bookService.findAllBorrowedBooksFromUser({
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

  goToPage(pageIndex: any) {
    this.page = pageIndex;
    this.findAllLoanedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllLoanedBooks();
  }

  goToLastPage() {
    this.page = this.loanedBooks.totalPages as number - 1;
    this.findAllLoanedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllLoanedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllLoanedBooks();
  }

  get isLastPage() {
    return this.page === this.loanedBooks.totalPages as number - 1;
  }
}
