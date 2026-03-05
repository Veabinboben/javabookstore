import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Publisher } from '../models/publisher';

@Injectable({
  providedIn: 'root',
})
export class PublishersService {
  private http = inject(HttpClient);

  private publishersSubject = new BehaviorSubject<Publisher[]>([]);
  publishers$ = this.publishersSubject.asObservable();

  constructor() { }

  getPublishers(filter : string) {
    const params = new HttpParams()
      .set('nameFilter', filter.toString())
    return this.http.get<Publisher[]>('/publishers/all', {params})
      .subscribe({
        next: (publishers) => {
          this.publishersSubject.next(publishers);  
          //this.loadingSubject.next(false);
        },
        error: () => {
          this.publishersSubject.next([]);
          //this.loadingSubject.next(false);
        }
      });
  }
}
