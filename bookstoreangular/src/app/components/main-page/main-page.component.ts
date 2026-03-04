import { Component, inject } from '@angular/core';
import { BookComponent } from '../book/book.component';
import { BooksService } from '../../services/books.service';
import { Book } from '../../models/book';
import { Observable } from 'rxjs';
import { CommonModule, NgForOf } from '@angular/common';
import {MatPaginatorModule, PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-main-page',
  imports: [BookComponent, NgForOf,CommonModule,MatPaginatorModule],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent {
  service: BooksService = inject(BooksService);

  //TODO observable vs behaviourSubject vs Signal vs whatever
  books: Book[] = [];

  length = 100; 
  pageSize = 10; 
  pageSizeOptions: number[] = [5, 10, 25, 100]; 
  pageIndex = 0; 

  ngOnInit() {
    this.loadData();
  }

  loadData() : void{
    this.service.getBooks(this.pageIndex,this.pageSize).subscribe(
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
}
