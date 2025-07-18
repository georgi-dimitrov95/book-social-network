import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookContextService {
  private bookIdSubject = new BehaviorSubject<number | null>(null);
  bookId$: Observable<number | null> = this.bookIdSubject.asObservable();

  setBookId(bookId: number) {
    this.bookIdSubject.next(bookId);
  }

  getBookId(): number | null {
    return this.bookIdSubject.value;
  }
}
