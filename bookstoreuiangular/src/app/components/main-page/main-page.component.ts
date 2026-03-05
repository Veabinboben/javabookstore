import { Component, inject } from '@angular/core';
import { BookComponent } from '../book/book.component';
import { BooksService } from '../../services/books.service';
import { Book } from '../../models/book';
import { filter, Observable, Subscription } from 'rxjs';
import { CommonModule, NgForOf } from '@angular/common';
import {MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main-page',
  imports: [BookComponent, NgForOf, CommonModule, MatPaginatorModule],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent {
  service: BooksService = inject(BooksService);
  router: Router = inject(Router);

  //TODO observable vs behaviourSubject vs Signal vs whatever
  books: Book[] = [];

  //TODO update this from aga bubub
  length = 100; 
  pageSize = 10; 
  pageSizeOptions: number[] = [5, 10, 25, 100]; 
  pageIndex = 0; 
  filter = "";

  private subscriptions = new Subscription();


  ngOnInit() {
    this.loadData();

    // Re-fetch when navigating back to this route
    const routerSub = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.loadData();
    });

    this.subscriptions.add(routerSub);
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

  loadData() : void{
    this.service.getBooks(this.pageIndex,this.pageSize,this.filter).subscribe(
      {
        next: (data) => {this.books = data;},
        error : (err) => {this.books = [];}
      },
    )
  }

  pageEvent(event: PageEvent) : void {
      this.pageIndex = event.pageIndex;
      this.loadData();
  }

  filterResults(value : string){
    this.pageIndex = 0;
    this.filter = value;
    this.loadData()
  }
}
