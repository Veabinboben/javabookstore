import { useContext, useEffect, useState } from "react";
import { AuthorContext, ReviewContext } from "../config/ServiceProvider";
import AsyncSelect from "react-select/async";
import type { SingleValue } from "react-select";
import { useLocation, useNavigate } from "react-router-dom";
import './form.css';
import type { Author } from "../models/author";

export function ReviewForm() {
    const reviewServce = useContext(ReviewContext);
    const authorService = useContext(AuthorContext);
    const location = useLocation();
    const navigate = useNavigate();

    const [submitting, setSubmitting] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const [authors, setAuthors] = useState<Author[]>([]);
    
    const [selectedAuthor, setSelectedAuthor] = useState<Author| null>(null);
    
    const params = new URLSearchParams(location.search);
    const id = Number(params.get("id") ?? -1);
    
    useEffect(() => {
        authorService?.getAuthors('').then((w) => setAuthors(w));
    },[]);

    async function addStock(formData: FormData) {
        setSubmitting(true);
        setError(null);
        setSuccess(false);     
        try {

            formData.set('bookId', id.toString());
            formData.set('authorId', selectedAuthor!.id.toString());

            await reviewServce?.addReview(formData);

            const next = new URLSearchParams();
            next.set('id', id.toString());
            navigate({ pathname: '/book', search: next.toString() }, { replace: true })

            setSuccess(true);
        } catch (err) {
            setError("Failed to add review. Please try again.");
        } finally {
            setSubmitting(false);
        }
    }

    const loadAuthors = (input : string) => {
        return authorService?.getAuthors(input || "");
    }
    const selectAuthor = (selected : SingleValue<Author>) => {
        setSelectedAuthor(selected);
    }

    return (
        <form action={addStock} encType="multipart/form-data">
            <input type="text"  name="contents" placeholder="Contents" required />
            <input type="number" max={10} min={0} name="rating" placeholder="Rating" required />
            <AsyncSelect 
                placeholder ={'Select Author'}
                defaultOptions={authors}
                onChange={selectAuthor}
                loadOptions={loadAuthors}
                value={selectedAuthor}
                getOptionValue={(opt) => String(opt.id)}
                getOptionLabel={(opt) => opt.name + ' ' + opt.middleName + ' ' + opt.surname}
            />
            <button type="submit" disabled={submitting}>
                {submitting ? "Adding..." : "Add review"}
            </button>
            {success && <p>Review added successfully!</p>}
            {error && <p>{error}</p>}
        </form>
    );
}