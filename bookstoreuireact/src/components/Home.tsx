import { useContext, useEffect, useState } from "react"
import { BookContext } from "../config/ServiceProvider"
import type { Page } from "../models/page";
import type { Book } from "../models/book";
import { Pagination } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./Home.module.css";
import { Book as BookView } from "./Book";

export function Home() {
    const location = useLocation();
    const navigate = useNavigate();
    const bookService = useContext(BookContext);

    const params = new URLSearchParams(location.search);
    //const q = params.get("q") ?? "";
    const page = Number(params.get("page") ?? -1);
    const filter = String(params.get("filter") ?? '');



    const [books, setBooks] = useState<Page<Book> | null>(null);
    const [input, setInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        console.log(page);
        setInput(filter);
        bookService?.getBooks(page - 1, 10, filter).then((b) => setBooks(b))
    },
        [page, filter])

    async function handleClick() {
        setLoading(true);
        setError(null);
        try {

            //TODO null checking aga
            //TODO add to querry params for page so on reload good
            const result = await bookService!.getBooks(0, 10, '');
            setBooks(result);

        } catch (e) {
            setError((e as Error).message || 'Failed to load books');
        } finally {
            setLoading(false);
        }
    };
    function handleBookClick(id: number) {
        try {
            const next = new URLSearchParams();
            next.set('id', id.toString());
            navigate({ pathname: '/book', search: next.toString() }, { replace: false })
        } catch (e) {
            setError((e as Error).message || 'Failed to load books');
        }
    };

    const handleChangePage = async (
        event: React.ChangeEvent<unknown> | null,
        page: number,
    ) => {

        const next = new URLSearchParams();
        next.set('page', page.toString());
        if (input != '')
            next.set('filter', filter.toString());

        navigate({ pathname: location.pathname, search: next.toString() }, { replace: false })

    };

    const handleSearch = async (
    ) => {
        const next = new URLSearchParams();

        next.set('page', '1');
        if (input != '')
            next.set('filter', input.toString());

        navigate({ pathname: location.pathname, search: next.toString() }, { replace: false })

    };

    return <>
        <div className={styles.searchContainer}>
            <div className={styles.searchRow}>
                <input
                    type="text"
                    value={input}
                    placeholder={"Input book title..."}
                    onChange={(e) => setInput(e.target.value)}
                    aria-label="Search input"
                />
                <button type="button" onClick={handleSearch} >
                    Search
                </button>
            </div>
            {books &&
                <div>
                    <div className={styles.resultsGrid}>
                        {
                            books.content.map((book) => (
                                <BookView book={book} onClickHandler={handleBookClick} />
                            ))
                        }
                    </div>
                    <br/>
                    <div className={styles.paginationWrapper}>
                        <Pagination page={page} count={books.totalPages} onChange={handleChangePage} />
                    </div>
                </div>
            }
        </div>
    </>
}