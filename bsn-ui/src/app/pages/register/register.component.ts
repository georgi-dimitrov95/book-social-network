import { Component } from '@angular/core';
import { RegisterRequest } from '../../services/models/register-request';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services/authentication.service'

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerRequest: RegisterRequest = {email: '', firstname: '', lastname: '', password: ''};
  errorMsg: Array<string> = [];

  constructor(
      private router: Router,
      private authService: AuthenticationService
    ) {}

    login() {
      this.router.navigate(['login']);
    }

    register() {
      this.errorMsg = [];
      this.authService.registerUser({body: this.registerRequest})
      .subscribe({next: (res) => {
//           green window with success if a RegisterResponse object was successfully returned or mayeb just check the status code?
// + button redirecting to Login page; probably need to introduce a boolean for deciding what to display on success/error
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
}
