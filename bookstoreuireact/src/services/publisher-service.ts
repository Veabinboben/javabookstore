import type { AxiosInstance } from "axios";
import type { Publisher } from "../models/publisher";

export class PublisherService {
    private http: AxiosInstance;

    constructor(http: AxiosInstance) {
        this.http = http;
    }

    async getPublishers(filter: string): Promise<Publisher[]> {
        const { data } = await this.http.get<Publisher[]>('/publishers/all', {
            params : {
                nameFilter: filter
            }
        });
        return data;
    }
}