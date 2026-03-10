import type { Book } from "./book";
import type { Warehouse } from "./warehouse";


export interface Stock {
    id: number;
    ammount: number;
    warehouse: Warehouse;
    book: Book;
}