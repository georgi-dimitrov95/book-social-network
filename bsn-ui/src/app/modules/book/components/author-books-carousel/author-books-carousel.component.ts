import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {BookService} from '../../../../services/services/book.service';

@Component({
  selector: 'app-author-books-carousel',
  standalone: false,
  templateUrl: './author-books-carousel.component.html',
  styleUrl: './author-books-carousel.component.scss'
})
export class AuthorBooksCarouselComponent implements OnChanges {

  @Input() authorName: string | undefined;
  books: BookResponse[] = [];
  chunkedBooks: BookResponse[][] = [];

  constructor(private bookService: BookService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['authorName'] && this.authorName) {
      this.populateCarousel();
    }
  }

  private populateCarousel(): void {
    this.bookService.getAllBooksByAuthor({'authorName': this.authorName!}).subscribe({
      next: (response: BookResponse[]) => {
        this.books = response;
        this.chunkBooks(this.books);
      }
    });
  }

  private chunkBooks(booksList: BookResponse[]): void {
    const chunkSize = 4;
    this.chunkedBooks = [];

    for (let i = 0; i < booksList.length; i += chunkSize) {
      this.chunkedBooks.push(booksList.slice(i, i + chunkSize));
    }
  }
}
