import { Component, inject, Input } from '@angular/core';
import { BooksService } from '../../services/books.service';
import { Book } from '../../models/book';
import { MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle, MatCardTitleGroup } from '@angular/material/card';

@Component({
  selector: 'app-book',
  imports: [MatCard, MatCardHeader, MatCardTitleGroup,MatCardContent,MatCardTitle, MatCardSubtitle],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent {
  
  @Input() book! : Book;

}
