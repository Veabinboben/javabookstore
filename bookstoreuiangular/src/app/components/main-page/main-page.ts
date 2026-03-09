import { Component, inject } from '@angular/core';
import { BookComponent } from '../book/book';
import { BooksService } from '../../services/books-service';
import { Book } from '../../models/book';
import { filter, Observable, Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import {MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import { ActivatedRoute, NavigationEnd, Params, Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-main-page',
  imports: [BookComponent, CommonModule, MatPaginatorModule, MatButton],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css'
})
export class MainPageComponent {
  private service: BooksService = inject(BooksService);
  private router: Router = inject(Router);
  private route : ActivatedRoute = inject(ActivatedRoute);
  //private route = inject(ActivatedRoute);


  //TODO observable vs behaviourSubject vs Signal vs whatever
  books$ = this.service.books$;

  //TODO update this from aga bubub
  length = 100; 
  pageSize = 10; 
  pageSizeOptions: number[] = [5, 10, 25, 100]; 
  pageIndex = Number(this.route.snapshot.queryParamMap.get('page')) ?? -1;

  filter = (this.route.snapshot.queryParamMap.get('filter')) ?? "";

  private subscriptions = new Subscription();

  constructor(){}

  ngOnInit() {
    this.loadData();

    // Re-fetch when navigating back to this route
    //TODO make so it returns to correct page (add page in route params)
    // const routerSub = this.router.events.pipe(
    //   filter(event => event instanceof NavigationEnd)
    // ).subscribe(() => {
    //   this.loadData();
    // });

    const querrySub = this.route.queryParams.subscribe((params: Params) => {
      this.pageIndex = Number(this.route.snapshot.queryParamMap.get('page')) ?? -1;
      this.filter = (this.route.snapshot.queryParamMap.get('filter')) ?? "";
      this.loadData();
    });

    //this.subscriptions.add(routerSub);
    this.subscriptions.add(querrySub);
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  loadData() : void{
    this.service.getBooks(this.pageIndex,this.pageSize,this.filter);
  }

  pageEvent(event: PageEvent) : void {
      this.pageIndex = event.pageIndex;
      const queryParams = { ...this.route.snapshot.queryParams, page: event.pageIndex };
      this.router.navigate([], { relativeTo: this.route, queryParams, queryParamsHandling: 'merge' });
      //this.loadData();
  }

  filterResults(value : string){
    this.pageIndex = 0;
    this.filter = value;
    const queryParams = { ...this.route.snapshot.queryParams, filter: value };
    this.router.navigate([], { relativeTo: this.route, queryParams, queryParamsHandling: 'merge' });
  }
}
