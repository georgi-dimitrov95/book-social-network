import { Component } from '@angular/core';
import { LoginRequest } from '../../services/models/login-request';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services/authentication.service'

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authRequest: LoginRequest = {email: '', password: ''};
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
//            mai nqmam roli i shte gurmi pri registraciq na nov user
          this.router.navigate(['books']);
        },
        error: (err) => {
          console.log(err);
          if (err.error.validationErrors) {
            this.errorMsg = err.error.validationErrors;
          } else {
            this.errorMsg.push(err.error.error);
          }
        }
      });
    }

  register(): void {
    this.router.navigate(['register'])
  }
}
