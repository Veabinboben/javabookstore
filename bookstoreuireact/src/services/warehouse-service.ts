import type { AxiosInstance } from "axios";
import type { Warehouse } from "../models/warehouse";

export class WarehouseService {
    private http: AxiosInstance;

    constructor(http: AxiosInstance) {
        this.http = http;
    }

    async getWarehouses(filter: string): Promise<Warehouse[]> {
        const { data } = await this.http.get<Warehouse[]>('/warehouses/all', {
            params: {
                adressFilter: filter
            }
        });
        return data;
    }
}