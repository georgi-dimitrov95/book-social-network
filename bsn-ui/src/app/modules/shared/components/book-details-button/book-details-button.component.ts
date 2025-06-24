import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-details-button',
  standalone: false,
  templateUrl: './book-details-button.component.html',
  styleUrl: './book-details-button.component.scss'
})
export class BookDetailsButtonComponent {

  @Input() bookId: any;

  constructor(
    private router: Router
  ) {}

  navigateToBookDetailsPage() {
    this.router.navigate(['books', 'book-details'], {queryParams: {bookId: this.bookId}});
  }
}
