import {Component, Input, OnInit} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {BookService} from '../../../../services/services/book.service';

@Component({
  selector: 'app-author-books-carousel',
  standalone: false,
  templateUrl: './author-books-carousel.component.html',
  styleUrl: './author-books-carousel.component.scss'
})
export class AuthorBooksCarouselComponent implements OnInit {

  @Input() authorName!: string;
  books: BookResponse[] = [];
  chunkedBooks: BookResponse[][] = [];

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.populateBookArrays();
    console.log(this.chunkedBooks);
  }

  private populateBookArrays(): void {
    this.authorName = "J.R.R. Tolkien";
    this.bookService.getAllBooksByAuthor({'authorName': this.authorName}).subscribe({
      next: (response: BookResponse[]) => {
        this.books = response;
        this.chunkBooks(this.books);
      }
    });
  }

  private chunkBooks(booksList: BookResponse[]): void {
    const chunkSize = 5;
    this.chunkedBooks = [];

    for (let i = 0; i < booksList.length; i += chunkSize) {
      this.chunkedBooks.push(booksList.slice(i, i + chunkSize));
    }
  }
}
