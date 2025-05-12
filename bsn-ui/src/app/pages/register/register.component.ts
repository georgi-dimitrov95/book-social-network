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
  success: boolean =  false;

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
          this.success = true;
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

    redirectToLogin() {
      this.router.navigate(['login']);
    }
}
