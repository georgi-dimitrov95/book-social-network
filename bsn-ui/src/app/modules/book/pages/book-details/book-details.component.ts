import {Component, OnInit} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {PageResponseFeedbackResponse} from '../../../../services/models/page-response-feedback-response';
import {BookService} from '../../../../services/services/book.service';
import {FeedbackService} from '../../../../services/services/feedback.service';
import {ActivatedRoute} from '@angular/router';

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
    private feedbackService: FeedbackService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.bookId = this.activatedRoute.snapshot.queryParams['bookId'];
    if (this.bookId) {
      this.bookService.findBookById({
        'id': this.bookId
      }).subscribe({
        next: (book) => {
          this.book = book;
          this.findAllFeedbacks();
        }
      });
    }
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
