/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { BorrowedBookResponse } from '../../models/borrowed-book-response';

export interface BorrowBookByTitleFromUser$Params {
  title: string;
  userId: number;
}

export function borrowBookByTitleFromUser(http: HttpClient, rootUrl: string, params: BorrowBookByTitleFromUser$Params, context?: HttpContext): Observable<StrictHttpResponse<BorrowedBookResponse>> {
  const rb = new RequestBuilder(rootUrl, borrowBookByTitleFromUser.PATH, 'post');
  if (params) {
    rb.query('title', params.title, {});
    rb.query('userId', params.userId, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<BorrowedBookResponse>;
    })
  );
}

borrowBookByTitleFromUser.PATH = '/books/borrow-from-user';
