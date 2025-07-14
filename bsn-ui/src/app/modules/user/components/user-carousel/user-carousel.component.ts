import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {BookResponse} from '../../../../services/models/book-response';
import {RegisterResponse} from '../../../../services/models/register-response';
import {UserService} from '../../../../services/services/user.service';
import {UserCardDto} from '../../../../services/models/user-card-dto';

@Component({
  selector: 'app-user-carousel',
  standalone: false,
  templateUrl: './user-carousel.component.html',
  styleUrl: './user-carousel.component.scss'
})
export class UserCarouselComponent implements OnChanges {

  @Input() bookTitle: string | undefined;
  users: UserCardDto[] = [];
  chunkedUsers: UserCardDto[][] = [];

  ngOnChanges(changes: SimpleChanges) {
    if (changes['bookTitle'] && this.bookTitle) {
      this.populateCarousel();
    }
  }

  constructor(private userService: UserService) {}

  private populateCarousel(): void {
    this.userService.getAllOwnersOfBookByBookTitle({'bookTitle': this.bookTitle!}).subscribe({
      next: (response: UserCardDto[]) => {
        this.users = response;
        this.chunkUsers(this.users);
      }
    });
  }

  private chunkUsers(usersList: UserCardDto[]): void {
    const chunkSize = 3;
    this.chunkedUsers = [];

    for (let i = 0; i < usersList.length; i += chunkSize) {
      this.chunkedUsers.push(usersList.slice(i, i + chunkSize));
    }
  }

}
