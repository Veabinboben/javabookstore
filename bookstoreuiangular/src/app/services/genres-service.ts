import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Genre } from '../models/genre';

@Injectable({
  providedIn: 'root',
})
export class GenresService {
  private http = inject(HttpClient);

  private genresSubject = new BehaviorSubject<Genre[]>([]);
  genres$ = this.genresSubject.asObservable();

  constructor() { }

  getGenres(filter : string) {
    const params = new HttpParams()
      .set('nameFilter', filter.toString())
    return this.http.get<Genre[]>('/genres/all', {params})
      .subscribe({
        next: (genres) => {
          this.genresSubject.next(genres);  
          //this.loadingSubject.next(false);
        },
        error: () => {
          this.genresSubject.next([]);
          //this.loadingSubject.next(false);
        }
      });
  }
}
