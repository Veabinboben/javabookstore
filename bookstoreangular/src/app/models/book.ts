import { Author } from "./author";
import { Genre } from "./genre";
import { Publisher } from "./publisher";

export interface Book {
    id : number;
    title: string;
    publishDate: Date;
    price: number;
    coverLink: string;
    authors: Set<Author>;
    genres: Set<Genre>;
    publishers: Set<Publisher>;
}
