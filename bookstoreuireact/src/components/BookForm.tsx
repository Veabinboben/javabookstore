import { useContext, useRef, useState } from "react";
import type { Book } from "../models/book";
import { BookContext } from "../config/ServiceProvider";

export function BookForm({ book }: { book?: Book }) {
    const bookService = useContext(BookContext);
    const fileRef = useRef<HTMLInputElement>(null);

    const [submitting, setSubmitting] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState<string | null>(null);

    async function addBook(formData: FormData) {
        setSubmitting(true);
        setError(null);
        setSuccess(false);
        for (const [key, value] of formData.entries()) {
            console.log(key, value);
        }        
        try {
            const file = fileRef.current?.files?.[0];
            // formData.set("publishDate", (new Date(formData.get("publishDate")!.toString())).toISOString().split('T')[0])
            // formData.set('title', formData.get('title')!);
            // formData.set('price', formData.get('price')!);
            if (file) {
                formData.set('file', file, file.name);
            }
            else{
                formData.delete('file');
            }
            await bookService?.addBook(formData);
            setSuccess(true);
        } catch (err) {
            setError("Failed to add book. Please try again.");
        } finally {
            setSubmitting(false);
        }
    }

    return (
        <form action={addBook} encType="multipart/form-data">
            <input type="number" name="price" placeholder="Price" required />
            <input type="text" name="title" placeholder="Title" required />
            <input type="date" name="publishDate" required />
            <input type="file" name="file" ref={fileRef} accept=".jpg,.png" />
            <button type="submit" disabled={submitting}>
                {submitting ? "Adding..." : "Add to Cart"}
            </button>
            {success && <p>Book added successfully!</p>}
            {error && <p>{error}</p>}
        </form>
    );
}