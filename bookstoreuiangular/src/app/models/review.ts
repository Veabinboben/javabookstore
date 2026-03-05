import { Author } from "./author";
import { Book } from "./book";

export interface Review{
    id: number;
    contents: string;
    rating: number;
    author: Author;
    book: Book;
}