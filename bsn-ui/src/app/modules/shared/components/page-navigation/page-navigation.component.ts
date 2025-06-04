import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-page-navigation',
  standalone: false,
  templateUrl: './page-navigation.component.html',
  styleUrl: './page-navigation.component.scss'
})
export class PageNavigationComponent {

  @Input() pages: any = [];

  @Input()
  page = 0;

  @Output() pageChange = new EventEmitter<number>();

  goToPage(page: number) {
    this.pageChange.emit(page);
  }

  goToFirstPage() {
    this.pageChange.emit(0);
  }

  goToLastPage() {
    this.pageChange.emit(this.pages.length - 1);
  }

  goToNextPage() {
    this.pageChange.emit(this.page + 1);
  }

  goToPreviousPage() {
    this.pageChange.emit(this.page - 1);
  }

  get isLastPage() {
    return this.page === this.pages.length - 1;
  }
}
