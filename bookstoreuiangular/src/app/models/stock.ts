import { Book } from "./book";
import { Warehouse } from "./warehouse";

export interface Stock{
    id: number;
    ammount: number;
    warehouse: Warehouse;
    book: Book;
}