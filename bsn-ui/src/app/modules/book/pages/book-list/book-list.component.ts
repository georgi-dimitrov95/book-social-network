import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService } from '../../../../services/services/book.service';
import { PageResponseBookResponse } from '../../../../services/models/page-response-book-response';
import { BookResponse } from '../../../../services/models/book-response';

@Component({
  selector: 'app-book-list',
  standalone: false,
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit {

  bookResponse: PageResponseBookResponse = {};
  page = 0;
  size = 3;
  pages: any = [];
  message = '';
  success = false;

  constructor(
    private bookService: BookService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.findAllBooks();
  }

  private findAllBooks() {
    this.bookService.findAllBooksOfCurrentUser({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (books) => {
          this.bookResponse = books;
          this.pages = Array(this.bookResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
        }
      });
  }

  gotToPage(page: number) {
    this.page = page;
    this.message = '';
    this.findAllBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.message = '';
    this.findAllBooks();
  }

  goToLastPage() {
    this.page = this.bookResponse.totalPages as number - 1;
    this.message = '';
    this.findAllBooks();
  }

  goToNextPage() {
    this.page++;
    this.message = '';
    this.findAllBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.message = '';
    this.findAllBooks();
  }

  get isLastPage() {
    return this.page === this.bookResponse.totalPages as number - 1;
  }

  borrowBook(book: BookResponse) {
    this.message = '';
    this.success = false;
    this.bookService.borrowBook({'bookId': book.id as number}).subscribe({
      next: () => {
        this.success = true;
        this.message = 'The book was successfully added to your list';
      },
      error: (err) => {
        console.log(err);
        this.success = false;
        this.message = err.error.error;
      }
    });
  }
}
