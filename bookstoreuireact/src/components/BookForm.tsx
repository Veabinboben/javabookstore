import { useContext, useEffect, useRef, useState } from "react";
import type { Book } from "../models/book";
import { AuthorContext, BookContext, GenreContext, PublisherContext } from "../config/ServiceProvider";
import type { Author } from "../models/author";
import type { Genre } from "../models/genre";
import type { Publisher } from "../models/publisher";
import AsyncSelect from "react-select/async";
import type { MultiValue } from "react-select";
import { useLocation, useNavigate } from "react-router-dom";
import './form.css';

export function BookForm() {
    const bookService = useContext(BookContext);
    const authorService = useContext(AuthorContext);
    const genreService = useContext(GenreContext);
    const publisherService = useContext(PublisherContext);
    const location = useLocation();
    const navigate = useNavigate();

    const fileRef = useRef<HTMLInputElement>(null);
    const [submitting, setSubmitting] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const [editableBook, setEditableBook] = useState<Book | null>(null);
    const [authors, setAuthors] = useState<Author[]>([]);
    const [genres, setGenres] = useState<Genre[]>([]);
    const [publishers, setPublishers] = useState<Publisher[]>([]);

    const [selectedGenres, setSelectedGenres] = useState<Genre[]>([]);
    const [selectedAuthors, setSelectedAuthors] = useState<Author[]>([]);
    const [selectedPublishers, setSelectedPublishers] = useState<Publisher[]>([]);

    const params = new URLSearchParams(location.search);
    const id = Number(params.get("id") ?? -1);

    useEffect(() => {
        if (id != -1) {
            bookService?.getBook(id).then((b) => {
                setEditableBook(b)
                setSelectedAuthors([...b.authors]);
                setSelectedGenres([...b.genres]);
                setSelectedPublishers([...b.publishers]);
            });
        }
        authorService?.getAuthors('').then((a) => setAuthors(a));
        genreService?.getGenres('').then((g) => setGenres(g));
        publisherService?.getPublishers('').then((p) => setPublishers(p));
    }, []);

    async function addBook(formData: FormData) {
        setSubmitting(true);
        setError(null);
        setSuccess(false);
        try {
            const file = fileRef.current?.files?.[0];
            if (editableBook != null) {
                formData.set('id', editableBook.id.toString());
                formData.set('imageUrl', editableBook.coverLink);
            }
            if (file) {
                formData.set('file', file, file.name);
            }
            else {
                formData.delete('file');
            }
            selectedGenres.forEach(genre => formData.append('genreIds', genre.id.toString()));
            selectedAuthors.forEach(author => formData.append('authorIds', author.id.toString()));
            selectedPublishers.forEach(publisher => formData.append('publisherIds', publisher.id.toString()));

            const book = await bookService?.addBook(formData);
            const next = new URLSearchParams();
            next.set('id', book!.id.toString());
            navigate({ pathname: '/book', search: next.toString() }, { replace: true })

            setSuccess(true);
        } catch (err) {
            setError("Failed to add book. Please try again.");
        } finally {
            setSubmitting(false);
        }
    }


    const loadGenres = (input: string) => {
        return genreService?.getGenres(input || "");
    }
    const selectGenres = (selected: MultiValue<Genre>) => {
        setSelectedGenres([...selected]);
    }

    const loadAuthors = (input: string) => {
        return authorService?.getAuthors(input || "");
    }
    const selectAuthors = (selected: MultiValue<Author>) => {
        setSelectedAuthors([...selected]);
    }

    const loadPublishers = (input: string) => {
        return publisherService?.getPublishers(input || "");
    }
    const selectPublishers = (selected: MultiValue<Publisher>) => {
        setSelectedPublishers([...selected]);
    }

    return (
        <form action={addBook} encType="multipart/form-data">
            <input type="number" defaultValue={editableBook?.price} name="price" placeholder="Price" required />
            <input type="text" defaultValue={editableBook?.title} name="title" placeholder="Title" required />
            <input type="date" defaultValue={editableBook?.publishDate.toString()} name="publishDate" required />
            <input type="file" name="file" ref={fileRef} accept=".jpg,.png" />
            <AsyncSelect
                isMulti
                placeholder={'Select genres'}
                defaultOptions={genres}
                onChange={selectGenres}
                loadOptions={loadGenres}
                value={selectedGenres}
                getOptionValue={(opt) => String(opt.id)}
                getOptionLabel={(opt) => opt.name}
            />
            <AsyncSelect
                isMulti
                placeholder={'Select authors'}
                defaultOptions={authors}
                onChange={selectAuthors}
                loadOptions={loadAuthors}
                value={selectedAuthors}
                getOptionValue={(opt) => String(opt.id)}
                getOptionLabel={(opt) => opt.name + ' ' + opt.middleName + ' ' + opt.surname}
            />
            <AsyncSelect
                isMulti
                placeholder={'Select publishers'}
                defaultOptions={publishers}
                onChange={selectPublishers}
                loadOptions={loadPublishers}
                value={selectedPublishers}
                getOptionValue={(opt) => String(opt.id)}
                getOptionLabel={(opt) => opt.name}
            />
            <button type="submit" disabled={submitting}>
                {submitting ? "Adding..." : "Add book"}
            </button>
            {success && <p>Book added successfully!</p>}
            {error && <p>{error}</p>}
        </form>
    );
}