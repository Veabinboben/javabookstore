import type { AxiosInstance } from "axios";
import type { Genre } from "../models/genre";

export class GenreService {
    private http: AxiosInstance;

    constructor(http: AxiosInstance) {
        this.http = http;
    }

    async getGenres(filter: string): Promise<Genre[]> {
        const { data } = await this.http.get<Genre[]>('/genres/all', {
            params : {
                nameFilter: filter
            }
        });
        return data;
    }
}