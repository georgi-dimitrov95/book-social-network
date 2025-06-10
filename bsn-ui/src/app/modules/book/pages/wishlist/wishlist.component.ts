import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {WishlistService} from '../../../../services/services/wishlist.service';
import {PageResponseWishlistEntryResponse} from '../../../../services/models/page-response-wishlist-entry-response';
import {BookResponse} from '../../../../services/models/book-response';
import {BookService} from '../../../../services/services/book.service';

@Component({
  selector: 'app-wishlist',
  standalone: false,
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.scss'
})
export class WishlistComponent implements OnInit {

  wishlistResponse: PageResponseWishlistEntryResponse = {};
  page = 0;
  size = 4;
  pages: any = [];
  message = "";
  success = false;

  constructor(
    private wishlistService: WishlistService,
    private bookService: BookService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.findAllWishlistedBooks();
  }

  private findAllWishlistedBooks() {
    this.wishlistService.getWishlistedBooksOfUser({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (wishlist) => {
          this.wishlistResponse = wishlist;
          this.pages = Array(this.wishlistResponse.totalPages)
            .fill(0)
            .map((x, i) => i);
        }
      });
  }

  onPageChange(page: number) {
    this.page = page;
    this.findAllWishlistedBooks();
  }

  borrowBook(book: BookResponse) {
    this.message = '';
    this.success = false;
    this.bookService.borrowBook({'bookId': book.id as number}).subscribe({
      next: () => {
        this.success = true;
        this.message = 'The book was successfully added to your wishlist';
      },
      error: (err) => {
        console.log(err);
        this.success = false;
        this.message = err.error.error;
      }
    });
  }

  removeBookFromWishlist(book: BookResponse) {
    this.wishlistService.removeBookFromWishlist({'bookId': book.id as number}).subscribe({
      next: () => {
        this.success = true;
        this.message = 'The book was successfully removed from your wishlist';
      },
      error: (err) => {
        console.log(err);
        this.success = false;
        this.message = err.error.error;
      }
    });
  }
}
