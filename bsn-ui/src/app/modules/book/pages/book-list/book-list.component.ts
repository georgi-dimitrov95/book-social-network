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
  size = 4;
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
    this.bookService.findAllBooksFromOtherOwners({
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

  onPageChange(page: number) {
    this.page = page;
    this.findAllBooks();
  }
}
