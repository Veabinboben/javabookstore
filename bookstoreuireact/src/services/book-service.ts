import type { AxiosInstance } from 'axios';
import type { Book } from '../models/book';
import type { Page } from '../models/page';

export class BookService {
    //TODO maybe change init
    private http: AxiosInstance;

    constructor(http: AxiosInstance) {
        this.http = http;
    }

    async getBooks(page : number, pageSize: number, filter: string): Promise<Page<Book>> {
        const { data } = await this.http.get<Page<Book>>('/books/all', {
            params : {
                page: page,
                pageSize : pageSize,
                titleFilter: filter
            }
        });
        return data;
    }

    async getBook(id: number): Promise<Book> {
        const { data } = await this.http.get<Book>('/books/getById', {
            params : {
                id: id,
            }
        });
        return data;
    }

    async addBook(form: FormData): Promise<Book> {
        const { data } = await this.http.post<Book>('/books/save', form,{
        headers: {
            'Content-Type': undefined, // force it explicitly
        },
    });
        return data;
    }
}