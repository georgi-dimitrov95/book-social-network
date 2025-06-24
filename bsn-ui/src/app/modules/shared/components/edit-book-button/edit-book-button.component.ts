import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-edit-book-button',
  standalone: false,
  templateUrl: './edit-book-button.component.html',
  styleUrl: './edit-book-button.component.scss'
})
export class EditBookButtonComponent {

  @Input() bookId: any;

  constructor(
    private router: Router
  ) {}

  editBook() {
    this.router.navigate(['books', 'manage-book', this.bookId]);
  }
}
