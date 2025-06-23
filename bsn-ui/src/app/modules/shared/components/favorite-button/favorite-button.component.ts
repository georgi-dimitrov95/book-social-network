import {Component, Input, OnInit} from '@angular/core';
import {WishlistService} from '../../../../services/services/wishlist.service';

@Component({
  selector: 'app-favorite-button',
  standalone: false,
  templateUrl: './favorite-button.component.html',
  styleUrl: './favorite-button.component.scss'
})
export class FavoriteButtonComponent implements OnInit {

  @Input() bookId: any;
  wishlisted:  boolean = false;
  message = "";

  constructor(
    private wishlistService: WishlistService
  ) {}

  ngOnInit(): void {
    this.wishlistService.isBookWishlistedByUser({'bookId': this.bookId as number}).subscribe({
      next: (res) => {
        this.wishlisted = res;
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  toggleFavorite() {
    if (this.wishlisted) {
      this.wishlistService.removeBookFromWishlist({'bookId': this.bookId as number}).subscribe({
        next: () => {
          this.wishlisted = false;
          this.message = "The book has been removed from your favorites";
        },
        error: (err) => {
          this.message = "We encountered an error during the operation. Please try again later.";
          console.log(err);
        }
      })
    } else {
      this.wishlistService.addBookToWishlist({'bookId': this.bookId as number}).subscribe({
        next: () => {
          this.wishlisted = true;
          this.message = "The book has been added to your favorites";
        },
        error: (err) => {
          this.message = "We encountered an error during the operation. Please try again later.";
          console.log(err);
        }
      })
    }
  }
}
