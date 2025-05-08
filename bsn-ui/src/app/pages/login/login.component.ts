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
          this.router.navigate(['books']);
        },
        error: (err) => {
          console.log(err);
          if (err.error instanceof Blob && err.error.type === 'application/json') {
            const reader = new FileReader();
            reader.onload = () => {
              const errorObj = JSON.parse(reader.result as string);
              this.errorMsg = errorObj.validationErrors || [errorObj.error || "Unknown error"];
            };
            reader.readAsText(err.error);
          }
        }
      });
    }

  register(): void {
    this.router.navigate(['register'])
  }
}
