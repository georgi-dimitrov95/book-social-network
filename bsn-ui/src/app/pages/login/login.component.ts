import { Component } from '@angular/core';
import { LoginUserDto } from '../../services/models/login-user-dto';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  authRequest: LoginUserDto = {email: '', password: ''};
  errorMsg: Array<string> = [];

  login(): void {

  }

  register(): void {

  }
}
