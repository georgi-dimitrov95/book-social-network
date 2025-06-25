import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BookService} from '../../../../services/services/book.service';

@Component({
  selector: 'app-share-book-button',
  standalone: false,
  templateUrl: './share-book-button.component.html',
  styleUrl: './share-book-button.component.scss'
})
export class ShareBookButtonComponent {

  @Input() bookId: any;
  @Input() shareable: boolean | undefined = false;
  @Output() shareableChange = new EventEmitter<boolean>();

  constructor(private bookService: BookService) {}

  toggleShareableStatus() {
    this.bookService.updateBookShareableStatus({
      'bookId': this.bookId as number
    }).subscribe({
      next: () => {
        this.shareable = !this.shareable;
        this.shareableChange.emit(this.shareable);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
}
