import type { Author } from "./author";
import type { Genre } from "./genre";
import type { Publisher } from "./publisher";


export interface Book {
    id: number;
    title: string;
    publishDate: Date;
    price: number;
    coverLink: string;
    authors: Set<Author>;
    genres: Set<Genre>;
    publishers: Set<Publisher>;
}