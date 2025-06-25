import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BookResponse } from '../../../../services/models/book-response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-card',
  standalone: false,
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent {

  private _book: BookResponse = {};
  private _manage = false;
  private _bookCover: string | undefined;

  constructor(
    private router: Router
  ) {}

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

  get manage(): boolean {
      return this._manage;
    }

    @Input()
    set manage(value: boolean) {
      this._manage = value;
    }

    @Output() private archive: EventEmitter<BookResponse> = new EventEmitter<BookResponse>();

    onArchive() {
      this.archive.emit(this._book);
    }
}
