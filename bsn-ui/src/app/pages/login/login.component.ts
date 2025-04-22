import { Component } from '@angular/core';
import { LoginUserDto } from '../../services/models/login-user-dto';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services/authentication.service'

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: LoginUserDto = {email: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  login() {
      this.errorMsg = [];
      this.authService.login({body: this.authRequest})
      .subscribe({next: (res) => {
//           this.tokenService.token = res.token as string;
          this.router.navigate(['books']);
        },
        error: (err) => {
          console.log(err);
          if (err.error.validationErrors) {
            this.errorMsg = err.error.validationErrors;
          } else {
            this.errorMsg.push(err.error.errorMsg);
          }
        }
      });
    }

  register(): void {
    this.router.navigate(['register'])
  }
}
