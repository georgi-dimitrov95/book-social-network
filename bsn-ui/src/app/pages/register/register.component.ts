import { Component } from '@angular/core';
import { RegisterUserDto } from '../../services/models/register-user-dto';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services/authentication.service'

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerRequest: RegisterUserDto = {email: '', firstname: '', lastname: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
      private router: Router,
      private authService: AuthenticationService
    ) {
    }

    login() {
      this.router.navigate(['login']);
    }

    register() {

    }
}
