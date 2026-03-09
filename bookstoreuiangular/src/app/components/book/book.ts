import { Component, inject, Input } from '@angular/core';
import { BooksService } from '../../services/books-service';
import { Book } from '../../models/book';
import { MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle, MatCardTitleGroup } from '@angular/material/card';
import { NavigationExtras, Router } from '@angular/router';

@Component({
  selector: 'app-book',
  imports: [MatCard, MatCardHeader, MatCardTitleGroup,MatCardContent,MatCardTitle, MatCardSubtitle],
  templateUrl: './book.html',
  styleUrl: './book.css'
})
export class BookComponent {
  private router = inject(Router);
  @Input() book! : Book;

  
  goToBook(id: number) {
    this.router.navigate(['/book', id]);
  }
  
  goToBookToEdit(book: Book) {
    const navigationExtras: NavigationExtras = {
    state: {
      data: book
      }
    };
    this.router.navigate(['/form'], navigationExtras);
  }

}
