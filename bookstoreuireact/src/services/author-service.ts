import type { AxiosInstance } from "axios";
import type { Author } from "../models/author";

export class AuthorService {
    private http: AxiosInstance;

    constructor(http: AxiosInstance) {
        this.http = http;
    }

    async getAuthors(filter: string): Promise<Author[]> {
        const { data } = await this.http.get<Author[]>('/authors/all', {
            params : {
                nameFilter: filter
            }
        });
        return data;
    }
}