import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { BookComponent } from '../book/book';
import { BooksService } from '../../services/books-service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-main-page',
  imports: [BookComponent, CommonModule, MatPaginatorModule],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css'
})
export class MainPageComponent implements OnInit, OnDestroy {
  private readonly service: BooksService = inject(BooksService);
  private readonly router: Router = inject(Router);
  private readonly route: ActivatedRoute = inject(ActivatedRoute);

  books$ = this.service.books$;

  length = 100;
  pageSize = 10;
  pageSizeOptions: number[] = [5, 10, 25, 100];
  pageIndex = Number(this.route.snapshot.queryParamMap.get('page')) ?? -1;

  filter = (this.route.snapshot.queryParamMap.get('filter')) ?? "";

  private subscriptions = new Subscription();

  constructor() { }

  ngOnInit() {
    this.loadData();

    const querrySub = this.route.queryParams.subscribe((params: Params) => {
      this.pageIndex = Number(this.route.snapshot.queryParamMap.get('page')) ?? -1;
      this.filter = (this.route.snapshot.queryParamMap.get('filter')) ?? "";
      this.loadData();
    });

    this.subscriptions.add(querrySub);
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  loadData(): void {
    this.service.getBooks(this.pageIndex, this.pageSize, this.filter);
  }

  pageEvent(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    const queryParams = { ...this.route.snapshot.queryParams, page: event.pageIndex };
    this.router.navigate([], { relativeTo: this.route, queryParams, queryParamsHandling: 'merge' });
  }

  filterResults(value: string) {
    this.pageIndex = 0;
    this.filter = value;
    const queryParams = { ...this.route.snapshot.queryParams, filter: value };
    this.router.navigate([], { relativeTo: this.route, queryParams, queryParamsHandling: 'merge' });
  }
}
