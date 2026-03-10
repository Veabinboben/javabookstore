import { useContext, useEffect, useState } from "react"
import { BookContext } from "../config/ServiceProvider"
import type { Page } from "../models/page";
import type { Book } from "../models/book";
import { Pagination } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./Home.module.css";

export function Home() {
    const location = useLocation();
    const navigate = useNavigate();
    const bookService = useContext(BookContext);

    const params = new URLSearchParams(location.search);
    //const q = params.get("q") ?? "";
    const page = Number(params.get("page") ?? -1);

    useEffect(() => {
        console.log(page);
        bookService?.getBooks(page - 1, 10, '').then((b) => setBooks(b))
    },
        [page])

    const [books, setBooks] = useState<Page<Book> | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

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
        //const result = await bookService!.getBooks(page-1, 10, '');
        //console.log(page);
        //setBooks(result);
        const next = new URLSearchParams();
        next.set('page', page.toString());

        navigate({ pathname: location.pathname, search: next.toString() }, { replace: false })

    };


    return <>
        <div>
            hihiha
            <button onClick={handleClick}>Load</button>

            {books &&
                <div>
                    <div className={styles.grid}>
                        {

                            books.content.map((book) => (
                                <div key={book.id} className={styles.book} onClick={() => handleBookClick(book.id)}> <img src={book.coverLink} height={200} width={200} /> </div>
                            )

                            )
                        }

                    </div>
                    <Pagination page={page} count={books.totalPages} onChange={handleChangePage} />
                </div>
            }
        </div>
    </>
}