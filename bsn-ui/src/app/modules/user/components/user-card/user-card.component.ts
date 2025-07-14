import {Component, Input} from '@angular/core';
import {UserCardDto} from '../../../../services/models/user-card-dto';
import {Router} from '@angular/router';

@Component({
  selector: 'app-user-card',
  standalone: false,
  templateUrl: './user-card.component.html',
  styleUrl: './user-card.component.scss'
})
export class UserCardComponent {

  private _user: UserCardDto = {};

  constructor(private router: Router) {}

  @Input()
  set user(value: UserCardDto) {
    this._user = value;
  }

  get user(): UserCardDto {
    return this._user;
  }

  viewProfile() {

  }
}
