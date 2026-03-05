import { Component, inject, signal } from '@angular/core';
import { form, FormField } from '@angular/forms/signals';
import { BookForm } from '../../models/book-form';
import { BooksService } from '../../services/books.service';
//TODO add error control
@Component({
  selector: 'app-book-form',
  imports: [FormField],
  templateUrl: './book-form.component.html',
  styleUrl: './book-form.component.css'
})
export class BookFormComponent {
  service: BooksService = inject(BooksService);


  bookModel = signal<BookForm>({
    id : null,
    title: '',
    publishDate: new Date(),
    price: 100,
    file: null,
    authorIds: [],
    genreIds: [],
    publisherIds: [],
    }// TODO CHECK
  );

  bookForm = form(this.bookModel);

  
  constructor(){
  }

  submit(){
    //console.log(this.bookModel())
    this.service.addBook(this.bookModel() ).subscribe(
      {
        next: (data) => {console.log(data)},
        error : (err) => {console.log(err)}
      },
    );
  }

  onFileChange(ev: Event) {
    const input = ev.target as HTMLInputElement;
    const file = input.files && input.files.length ? input.files[0] : null;
    this.bookForm.file().value.set( file );
  }
}
