import {Component, OnInit} from '@angular/core';
import {BookRequest} from '../../../../services/models/book-request';
import {BookService} from '../../../../services/services/book.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-manage-book',
  standalone: false,
  templateUrl: './manage-book.component.html',
  styleUrl: './manage-book.component.scss'
})
export class ManageBookComponent implements OnInit {

  errorMsg: Array<string> = [];
  bookRequest: BookRequest = {
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  };
  selectedCover: any;
  displayCover: string | undefined;

  constructor(
    private bookService: BookService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if (bookId) {
      this.bookService.findBookById({
        'id': bookId
      }).subscribe({
        next: (book) => {
          this.bookRequest = {
            id: book.id,
            title: book.title as string,
            authorName: book.authorName as string,
            isbn: book.isbn as string,
            synopsis: book.synopsis as string,
            shareable: book.shareable
          };
          this.displayCover='data:image/jpg;base64,' + book.cover;
        }
      });
    }
  }

  saveBook() {
    this.bookService.saveBook({
      body: this.bookRequest
    }).subscribe({
      next: (book) => {
        this.bookService.uploadBookCoverPicture({
          'bookId': book.id as number,
          body: {
            file: this.selectedCover
          }
        }).subscribe({
          next: () => {
            this.router.navigate(['/books/my-books']);
          }
        });
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  onFileSelected(event: any) {
    this.selectedCover = event.target.files[0];
    console.log(this.selectedCover);

    if (this.selectedCover) {

      const reader = new FileReader();
      reader.onload = () => {
        this.displayCover = reader.result as string;
      };
      reader.readAsDataURL(this.selectedCover);
    }
  }
}
