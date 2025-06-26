import {Component, Input} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-thumbnail',
  standalone: false,
  templateUrl: './book-thumbnail.component.html',
  styleUrl: './book-thumbnail.component.scss'
})
export class BookThumbnailComponent {

  private _book: BookResponse = {};

  constructor(private router: Router) {}

  @Input()
  set book(value: BookResponse) {
    this._book = value;
  }

  get book(): BookResponse {
    return this._book;
  }

  get bookCover(): string | undefined {
    if (this._book.cover) {
      return 'data:image/jpg;base64,' + this._book.cover
    }
    return '';
  }

  goToBookDetails() {
    this.router.navigate(['books', 'book-details'], {queryParams: {bookId: this.book.id}});
  }
}
