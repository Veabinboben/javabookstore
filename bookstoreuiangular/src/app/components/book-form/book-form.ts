import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { form, FormField, required } from '@angular/forms/signals';
import { BookForm } from '../../models/book-form';
import { BooksService } from '../../services/books-service';
import { AuthorsService } from '../../services/authors-service';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { CommonModule } from '@angular/common';
import { GenresService } from '../../services/genres-service';
import { Author } from '../../models/author';
import { Genre } from '../../models/genre';
import { PublishersService } from '../../services/publishers-service';
import { Publisher } from '../../models/publisher';
import { Router } from '@angular/router';
import { Book } from '../../models/book';
import { Validators } from '@angular/forms';


@Component({
  selector: 'app-book-form',
  imports: [
    CommonModule,
    FormField,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,
  ],
  templateUrl: './book-form.html',
  styleUrl: './book-form.css'
})
export class BookFormComponent {
  private booksService   = inject(BooksService);
  private authorsService = inject(AuthorsService);
  private genresService  = inject(GenresService);
  private publishersService  = inject(PublishersService);


  bookModel = signal<BookForm>({
    id:           null,
    title:        '',
    publishDate:  new Date(),
    price:        100,
    file:         null,
    authorIds:    [],
    genreIds:     [],
    publisherIds: [],
  });

  bookForm = form(this.bookModel, (schemaPath) => {
    required(schemaPath.title, {message: 'Title is required'});
    required(schemaPath.price, {message: 'Price is required'});
    required(schemaPath.publishDate, {message: 'Publish date is required'});
    //email(schemaPath.email, {message: 'Enter a valid email address'});
  });

  authors$        = this.authorsService.authors$;
  selectedAuthors = signal<Author[]>([]);
  selectedAuthorIds = computed(() => this.selectedAuthors().map(a => a.id));
  
  genres$        = this.genresService.genres$;
  selectedGenres = signal<Genre[]>([]);
  selectedGenreIds = computed(() => this.selectedGenres().map(a => a.id));

  publishers$        = this.publishersService.publishers$;
  selectedPublishers = signal<Publisher[]>([]);
  selectedPublishersIds = computed(() => this.selectedPublishers().map(a => a.id));

  authorSearch = signal('');
  genreSearch = signal('');
  publisherSearch = signal('');

   constructor(private router: Router) {
    // TODO what to do with photos THONK
    const navigation = this.router.currentNavigation();
    if (navigation?.extras.state){
      const currentBook = navigation?.extras.state['data'] as Book;
      this.bookForm.id().value.set(currentBook.id);
      this.bookForm.title().value.set(currentBook.title);
      this.bookForm.price().value.set(currentBook.price);
      this.bookForm.publishDate().value.set(currentBook.publishDate);
      //TODO this prob works wroooooong
      this.selectedPublishers.set([...currentBook.publishers])
      this.selectedAuthors.set([...currentBook.authors])
      this.selectedGenres.set([...currentBook.genres])
      
    }
  }

  //TODO add separate component for this prob
  onAuthorSearch(value: string): void {
    this.authorSearch.set(value);
    this.authorsService.getAuthors(value);
  }

  onAuthorSelected(event: MatAutocompleteSelectedEvent): void {
    const author: Author = event.option.value;

    if (!this.selectedAuthors().some(a => a.id === author.id)) {
      this.selectedAuthors.update(prev => [...prev, author]);
      this.bookForm.authorIds().value.set(this.selectedAuthorIds());
    }

    this.authorSearch.set('');
  }

  removeAuthor(author: Author): void {
    this.selectedAuthors.update(prev => prev.filter(a => a.id !== author.id));
    this.bookForm.authorIds().value.set(this.selectedAuthorIds());
  }

  onGenreSearch(value: string): void {
    this.genreSearch.set(value);
    this.genresService.getGenres(value);
    //console.log(this.genres$)
  }

  onGenreSelected(event: MatAutocompleteSelectedEvent): void {
    const genre: Genre = event.option.value;

    if (!this.selectedGenres().some(a => a.id === genre.id)) {
      this.selectedGenres.update(prev => [...prev, genre]);
      this.bookForm.genreIds().value.set(this.selectedGenreIds());
    }

    this.genreSearch.set('');
  }

  removeGenre(genre: Genre): void {
    this.selectedGenres.update(prev => prev.filter(a => a.id !== genre.id));
    this.bookForm.genreIds().value.set(this.selectedGenreIds());
  }

  onPublisherSearch(value: string): void {
    this.publisherSearch.set(value);
    this.publishersService.getPublishers(value);
    //console.log(this.genres$)
  }

  onPublisherSelected(event: MatAutocompleteSelectedEvent): void {
    const publisher: Publisher = event.option.value;

    if (!this.selectedPublishers().some(a => a.id === publisher.id)) {
      this.selectedPublishers.update(prev => [...prev, publisher]);
      this.bookForm.publisherIds().value.set(this.selectedPublishersIds());
    }

    this.publisherSearch.set('');
  }

  removePublisher(publisher: Publisher): void {
    this.selectedPublishers.update(prev => prev.filter(a => a.id !== publisher.id));
    this.bookForm.publisherIds().value.set(this.selectedPublishersIds());
  }

  onFileChange(ev: Event): void {
    const input = ev.target as HTMLInputElement;
    const file  = input.files?.[0] ?? null;
    this.bookForm.file().value.set(file);
  }

  //TODO add form init for making edits -_-

  submitForm(): void {
    if (this.bookForm().invalid()) return;
    this.booksService.addBook(this.bookModel()).subscribe({
      next:  (data) => {
        console.log('Saved:', data)
        this.router.navigate(['/book', data.id], );

      },
      error: (err)  => console.error('Error:', err),
    });
  }
}