export interface BookForm {
    id: number | null;
    title: string;
    publishDate: Date;
    price: number;
    file: File | null;
    authorIds: number[];
    genreIds: number[];
    publisherIds: number[];
}
