import { inject, Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Author } from '../models/author';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthorsService {
  private readonly http = inject(HttpClient);

  private authorsSubject = new BehaviorSubject<Author[]>([]);
  authors$ = this.authorsSubject.asObservable();

  constructor() { }

  getAuthors(filter: string) {
    const params = new HttpParams()
      .set('nameFilter', filter.toString())
    return this.http.get<Author[]>('/authors/all', { params })
      .subscribe({
        next: (authors) => {
          this.authorsSubject.next(authors);
        },
        error: () => {
          this.authorsSubject.next([]);
        }
      });
  }
}
