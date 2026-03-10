import type { Author } from "./author";
import type { Book } from "./book";

export interface Review {
    id: number;
    contents: string;
    rating: number;
    author: Author;
    book: Book;
}