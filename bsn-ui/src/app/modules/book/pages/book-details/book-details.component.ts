import {Component, OnInit} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {PageResponseFeedbackResponse} from '../../../../services/models/page-response-feedback-response';
import {BookService} from '../../../../services/services/book.service';
import {FeedbackService} from '../../../../services/services/feedback.service';
import {ActivatedRoute} from '@angular/router';
import {BookContextService} from '../../../../services/context/book-context.service';

@Component({
  selector: 'app-book-details',
  standalone: false,
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.scss'
})
export class BookDetailsComponent implements OnInit {

  book: BookResponse = {};
  feedbacks: PageResponseFeedbackResponse = {};
  page = 0;
  size = 5;
  pages: any = [];
  private bookId = 0;

  constructor(
    private bookService: BookService,
    private bookContext:  BookContextService,
    private feedbackService: FeedbackService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      const newBookId = +params['bookId'];

      if (newBookId !== this.bookId) {
        this.bookId = newBookId;
        this.loadBookDetails();
      }
    });
    this.bookContext.setBookId(this.bookId);
  }

  private loadBookDetails():  void {
    this.bookService.findBookById({ 'id': this.bookId }).subscribe({
      next: (book) => {
        this.book = book;
        this.findAllFeedbacks();
      }
    });
  }

  private findAllFeedbacks() {
    this.feedbackService.findAllFeedbacksByBook({
      'bookId': this.bookId,
      page: this.page,
      size: this.size
    }).subscribe({
      next: (feedbacks) => {
        this.feedbacks = feedbacks;
      }
    });
  }
}
