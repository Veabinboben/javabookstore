import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BooksService } from '../../services/books-service';
import { AsyncPipe, CommonModule } from '@angular/common';
import { BookComponent } from "../book/book";
@Component({
  selector: 'app-book-view',
  imports: [CommonModule, BookComponent],
  templateUrl: './book-view.html',
  styleUrl: './book-view.css',
})
export class BookView {
  private route = inject(ActivatedRoute);
  private booksService = inject(BooksService);
  id : number = Number(this.route.snapshot.paramMap.get('id') ?? -1);
  
  book$ = this.booksService.book$;

  ngOnInit() {
    this.loadBook();
  }

  ngOnDestroy() {
    this.booksService.cleanBook();
  }

  loadBook(){
    this.booksService.getBookById(this.id);
  }
}
