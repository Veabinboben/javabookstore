import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Stock } from '../models/stock';
import { StockForm } from '../models/stock-form';

@Injectable({
    providedIn: 'root',
})
export class StocksService {
    private readonly http = inject(HttpClient);

    private stocksSubject = new BehaviorSubject<Stock[]>([]);
    stocks$ = this.stocksSubject.asObservable();

    constructor() { }

    getStocks(boodId: number) {
        const params = new HttpParams()
            .set('bookId', boodId.toString())
        return this.http.get<Stock[]>('/stocks/all', { params })
            .subscribe({
                next: (stocks) => {
                    this.stocksSubject.next(stocks);
                },
                error: () => {
                    this.stocksSubject.next([]);
                }
            });
    }

    addStock(form: StockForm): Observable<Stock> {
        const formData = new FormData();

        formData.append('stock', form.stock.toString());
        formData.append('warehouseId', form.warehouseId!.toString());
        formData.append('bookId', form.bookId!.toString());

        return this.http.post<Stock>('/stocks/save', formData);
    }

}
