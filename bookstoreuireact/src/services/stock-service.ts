import type { AxiosInstance } from 'axios';
import type { Stock } from '../models/stock';

export class StockService {
    private http: AxiosInstance;

    constructor(http: AxiosInstance) {
        this.http = http;
    }

    async getStocks(bookId: number): Promise<Stock[]> {
        const { data } = await this.http.get<Stock[]>('/stocks/all', {
            params: {
                bookId: bookId
            }
        });
        return data;
    }

    async addStock(form: FormData): Promise<Stock> {
        const { data } = await this.http.post<Stock>('/stocks/save', form, {
            headers: {
                'Content-Type': undefined,
            },
        });
        return data;
    }
}