import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Book } from '../models/book';
import { Page } from '../models/page';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080'

  constructor() { }

  getBooks(index: number, size : number, filter : string) : Observable<Book[]> {
    const params = new HttpParams()
      .set('page', index.toString())
      .set('titleFilter', filter.toString())
      .set('pageSize', size.toString());
    return this.http.get<Page>(this.baseUrl + '/books/all', {params})
      .pipe(map(page => page.content));
  }

  addBook(index: number, size : number, filter : string) : Observable<Book[]> {
    const params = new HttpParams()
      .set('page', index.toString())
      .set('titleFilter', filter.toString())
      .set('pageSize', size.toString());
    return this.http.get<Page>(this.baseUrl + '/books/all', {params})
      .pipe(map(page => page.content));
  }

  // getAllHousingLocations(): HousingLocation[] {
  //   return this.housingLocationList;
  // }
  // getHousingLocationById(id: number): HousingLocation | undefined {
  //   return this.housingLocationList.find((housingLocation) => housingLocation.id === id);
  // }
}
