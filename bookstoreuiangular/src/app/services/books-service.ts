import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Book } from '../models/book';
import { Page } from '../models/page';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { BookForm } from '../models/book-form';

//TODO make separate layer for business logic and state (maybe)

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  private http = inject(HttpClient);

  private booksSubject = new BehaviorSubject<Page<Book> | null>(null);
  books$ = this.booksSubject.asObservable();

  //This may be a cringo gringo lmao
  private bookByIdSubject = new BehaviorSubject<Book | null>(null);
  book$ = this.bookByIdSubject.asObservable();

  constructor() { }

  getBooks(index: number, size : number, filter : string) {
    const params = new HttpParams()
      .set('page', index.toString())
      .set('titleFilter', filter.toString())
      .set('pageSize', size.toString());
    return this.http.get<Page<Book>>('/books/all', {params})
      .pipe().subscribe({
        next: (books) => {
          books.content.map(book => {
            book.publishDate = new Date(book.publishDate);
            return book;
          })
          console.log(books);
          this.booksSubject.next(books);  // ← push new state
          //this.loadingSubject.next(false);
        },
        error: () => {
          this.booksSubject.next(null);
          //this.loadingSubject.next(false);
        }
      });
  }

  getBookById(id: number) {
    const params = new HttpParams()
      .set('id', id.toString());
    return this.http.get<Book>('/books/getById', {params})
      .pipe(map(book => {
        book.publishDate = new Date(book.publishDate);
        return book;
      })).subscribe({
        next: (book) => {
          this.bookByIdSubject.next(book);  // ← push new state
          //this.loadingSubject.next(false);
        },
        error: () => {
          this.bookByIdSubject.next(null);
          //this.loadingSubject.next(false);
        }
      });
  }

  cleanBook(){
    this.bookByIdSubject.next(null);
  }

  addBook(form : BookForm) : Observable<Book>  {
    const formData = new FormData();

    if (form.id != null) {
      formData.append('id', form.id.toString());
    }
    formData.append('title', form.title);
    formData.append('publishDate', form.publishDate.toISOString().split('T')[0]);
    formData.append('price', form.price.toString());

    form.authorIds.forEach(id => formData.append('authorIds', id.toString()));
    form.publisherIds.forEach(id => formData.append('publisherIds', id.toString()));
    form.genreIds.forEach(id => formData.append('genreIds', id.toString()));

    if (form.file) {
      formData.append('file', form.file, form.file.name);
    }
    console.log(formData);

    return this.http.post<Book>('/books/save', formData);
  }

  deleteBook(id: number) : Observable<void>  {
    const params = new HttpParams()
      .set('id', id.toString());
    return this.http.delete<void>('/books/delete', {params});
  }

  // getAllHousingLocations(): HousingLocation[] {
  //   return this.housingLocationList;
  // }
  // getHousingLocationById(id: number): HousingLocation | undefined {
  //   return this.housingLocationList.find((housingLocation) => housingLocation.id === id);
  // }
}
