/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { BookResponse } from '../../models/book-response';

export interface GetAllBooksByAuthor$Params {
  authorName: string;
}

export function getAllBooksByAuthor(http: HttpClient, rootUrl: string, params: GetAllBooksByAuthor$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<BookResponse>>> {
  const rb = new RequestBuilder(rootUrl, getAllBooksByAuthor.PATH, 'get');
  if (params) {
    rb.query('authorName', params.authorName, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<BookResponse>>;
    })
  );
}

getAllBooksByAuthor.PATH = '/books';
