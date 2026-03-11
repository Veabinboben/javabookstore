import { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import type { Book } from "../models/book";
import { BookContext } from "../config/ServiceProvider";
import { Book as BookComp } from "./Book";
import styles from "./BookView.module.css"

export function BookView() {
    const location = useLocation();
    const navigate = useNavigate();
    const bookService = useContext(BookContext);
    

    const params = new URLSearchParams(location.search);
    const id = Number(params.get("id") ?? -1);

    const [book, setBook] = useState<Book | null>(null);
    
    useEffect(() => {
        bookService?.getBook(id).then((b) => setBook(b))
    },[])

    const editBook = () => {
        const next = new URLSearchParams();
        next.set('id', book!.id.toString());

        navigate({ pathname: '/book/edit', search: next.toString() }, {replace: false })
    }

    return (
        <>
            <div className={styles.column}>
            <button type = "button" onClick={editBook}>aSasdsahjgdahsgdjhas</button>
            {book &&
                <div>
                    <BookComp book = {book}/>
                </div>
            }
            </div>
        </>
    )

}