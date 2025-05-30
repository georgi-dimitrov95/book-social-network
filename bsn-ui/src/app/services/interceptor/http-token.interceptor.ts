import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import { TokenService } from '../token/token.service';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(
    private tokenService: TokenService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.tokenService.getToken();
    const isAuthRequest = request.url.startsWith('/api/auth');

    if (token && !isAuthRequest) {
      const authReq = request.clone({
        headers: new HttpHeaders({Authorization: 'Bearer ' + token})
      });
      return next.handle(authReq);
    }
    return next.handle(request);
  }
}
