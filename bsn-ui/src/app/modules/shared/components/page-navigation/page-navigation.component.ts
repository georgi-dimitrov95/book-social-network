import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-page-navigation',
  standalone: false,
  templateUrl: './page-navigation.component.html',
  styleUrl: './page-navigation.component.scss'
})
export class PageNavigationComponent implements OnInit{

  @Input() pages: any = [];

  @Input()
  page = 0;

  @Output() pageChange = new EventEmitter<number>();

  ngOnInit(): void {

  }

  goToPage(page: number) {
    this.page = page;
    this.pageChange.emit(this.page);
  }

  goToFirstPage() {
    this.page = 0;
    this.pageChange.emit(this.page);
  }

  goToLastPage() {
    this.page = this.pages.length - 1;
    this.pageChange.emit(this.page);
  }

  goToNextPage() {
    this.page++;
    this.pageChange.emit(this.page);
  }

  goToPreviousPage() {
    this.page--;
    this.pageChange.emit(this.page);
  }

  get isLastPage() {
    return this.page === this.pages.length - 1;
  }
}
