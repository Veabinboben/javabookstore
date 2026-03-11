export interface ReviewForm {
    contents: string;
    rating: number;
    authorId: number | null;
    bookId: number | null;
}
