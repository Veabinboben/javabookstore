import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Genre } from '../models/genre';
import { Warehouse } from '../models/warehouse';

@Injectable({
  providedIn: 'root',
})
export class WarehousesService {
  
  private http = inject(HttpClient);

  private warehousesSubject = new BehaviorSubject<Warehouse[]>([]);
  warehouses$ = this.warehousesSubject.asObservable();

  constructor() { }

  getWarehouses(filter : string) {
    const params = new HttpParams()
      .set('adressFilter', filter.toString())
    return this.http.get<Warehouse[]>('/warehouses/all', {params})
      .subscribe({
        next: (warehouses) => {
          this.warehousesSubject.next(warehouses);  
          //this.loadingSubject.next(false);
        },
        error: () => {
          this.warehousesSubject.next([]);
          //this.loadingSubject.next(false);
        }
      });
  }

}
