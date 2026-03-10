import { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import type { Book } from "../models/book";
import { BookContext } from "../config/ServiceProvider";

export function Book() {
    const location = useLocation();
    const navigate = useNavigate();
    const bookService = useContext(BookContext);
    

    const params = new URLSearchParams(location.search);
    const id = Number(params.get("id") ?? -1);

    const [book, setBook] = useState<Book | null>(null);
    
    useEffect(() => {
        bookService?.getBook(id).then((b) => setBook(b))
    },[])

    return (
        <>
            {book &&
                <div>
                    {book.title}
                </div>
            }
        </>
    )

}