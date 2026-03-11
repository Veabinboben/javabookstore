import { useCallback, useContext, useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import type { Book } from "../models/book";
import { BookContext, ReviewContext, StockContext } from "../config/ServiceProvider";
import { Book as BookComp } from "./Book";
import styles from "./BookView.module.css"
import type { Review } from "../models/review";
import type { Stock } from "../models/stock";

export function BookView() {
    const location = useLocation();
    const navigate = useNavigate();
    const bookService = useContext(BookContext);
    const reviewService = useContext(ReviewContext);
    const stockService = useContext(StockContext);

    const params = new URLSearchParams(location.search);
    const id = Number(params.get("id") ?? -1);

    const [book, setBook] = useState<Book | null>(null);
    const [stocks, setStocks] = useState<Stock[]>([]);
    const [reviews, setReviews] = useState<Review[]>([]);
    const loadingRef = useRef(false);
    const sentinelRef = useRef<HTMLDivElement | null>(null);

    const page = useRef(0);
    const totalItems = useRef(0);
    const pageSize = 5;

    useEffect(() => {
        bookService?.getBook(id).then((b) => setBook(b))
        stockService?.getStocks(id).then((s) => setStocks(s));
        reviewService?.getReviews(page.current, pageSize, id).then((r) => {
            setReviews(r.content)
            page.current = 0;
            totalItems.current = (r.totalElements);
        })
    }, [])

    const loadNext = useCallback(() => {
        if (loadingRef.current) return;
        if (totalItems !== null && reviews.length >= totalItems.current) return;
        loadingRef.current = true;
        const nextPage = page.current + 1;
        reviewService?.getReviews(nextPage, pageSize, id)
            .then(r => {
                setReviews(prev => [...prev ?? [], ...r.content]);
                page.current = nextPage;
            })
            .finally(() => { loadingRef.current = false; });
    }, [id, pageSize, reviewService, reviews.length]);

    useEffect(() => {
        if (!sentinelRef.current) return;

        const observer = new IntersectionObserver(entries => {
            if (entries[0].isIntersecting) {
                loadNext();
            }
        });

        observer.observe(sentinelRef.current);

        return () => observer.disconnect();
    }, [loadNext]);

    const editBook = () => {
        const next = new URLSearchParams();
        next.set('id', book!.id.toString());

        navigate({ pathname: '/book/edit', search: next.toString() }, { replace: false })
    }
    const deleteBook = () => {
        bookService?.deleteBook(id).then((e) => {
            navigate({ pathname: '/' }, { replace: true })
        })
    }
    const addStock = () => {
        const next = new URLSearchParams();
        next.set('id', book!.id.toString());

        navigate({ pathname: '/stock/add', search: next.toString() }, { replace: false })
    }
    const addReview = () => {
        const next = new URLSearchParams();
        next.set('id', book!.id.toString());

        navigate({ pathname: '/review/add', search: next.toString() }, { replace: false })
    }

    return (
        <>
            {book &&
                <div className={styles.column}>
                    <div className={styles.row}>
                        <button type="button" onClick={editBook}>Edit</button>
                        <button type="button" onClick={deleteBook}>Delete</button>
                    </div>
                    <div className={styles.row}>
                        <div className={styles.column}>
                            <BookComp book={book} />
                            <div className={styles.card}>
                                <div className={styles.row}>
                                    {
                                        (book.genres && [...book.genres].length != 0) ? [...book.genres].map(
                                            (g) => <div>{g.name}</div>
                                        ) : (<div>No Genres</div>)
                                    }
                                </div>
                                <div className={styles.row}>
                                    {
                                        (book.publishers && [...book.publishers].length != 0) ? ([...book.publishers].map(
                                            (g) => <div>{g.name}</div>
                                        )) : (<div>No Publishers</div>)
                                    }
                                </div>
                            </div>
                        </div>

                        <div>
                            <button type="button" onClick={addStock} >Add Stock</button>
                            {stocks && stocks.length != 0 ?
                                stocks.map((s) => (
                                    <div key={s.id} className={styles.card}>{s.warehouse.city.name}, {s.warehouse.adress} : {s.ammount}</div>
                                )) : <div>No Stocks</div>
                            }
                        </div>
                    </div>
                    <button type="button" onClick={addReview}> Add Review</button>
                    <div>
                        {reviews &&
                            reviews.map((r) => (
                                <div key={r.id} className={styles.card}>
                                    <strong>{r.author.name} {r.author.middleName} {r.author.surname}</strong>
                                    <div>{r.rating} ⭐</div>
                                    <div>{r.contents}</div>
                                </div>
                            ))
                        }
                        <div ref={sentinelRef} ><br /></div>
                    </div>
                </div>
            }
        </>
    )

}