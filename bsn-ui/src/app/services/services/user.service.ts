/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getAllOwnersOfBookByBookTitle } from '../fn/user/get-all-owners-of-book-by-book-title';
import { GetAllOwnersOfBookByBookTitle$Params } from '../fn/user/get-all-owners-of-book-by-book-title';
import { UserCardDto } from '../models/user-card-dto';

@Injectable({ providedIn: 'root' })
export class UserService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllOwnersOfBookByBookTitle()` */
  static readonly GetAllOwnersOfBookByBookTitlePath = '/users';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllOwnersOfBookByBookTitle()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOwnersOfBookByBookTitle$Response(params: GetAllOwnersOfBookByBookTitle$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<UserCardDto>>> {
    return getAllOwnersOfBookByBookTitle(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllOwnersOfBookByBookTitle$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOwnersOfBookByBookTitle(params: GetAllOwnersOfBookByBookTitle$Params, context?: HttpContext): Observable<Array<UserCardDto>> {
    return this.getAllOwnersOfBookByBookTitle$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<UserCardDto>>): Array<UserCardDto> => r.body)
    );
  }

}
