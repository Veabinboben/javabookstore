import type { Book } from "../models/book";
import styles from "./Book.module.css";
import placeholderImage from '../../public/placeholder.png';

export function Book({ book, onClickHandler }: { book: Book, onClickHandler?: (id: number) => void }) {

    return (
        <div className={styles.container} onClick={() => onClickHandler?.(book.id)}>
            <p>{book.title}</p>
            <div className={styles.row}>
                {[...book.authors].map((author) => (
                    <div> {author.name}</div>
                ))}
            </div>
            <div key={book.id} className={styles.bookimage}>
                <img src={book.coverLink || placeholderImage} alt="no image :("
                    onError={(e) => {
                        e.currentTarget.onerror = null;
                        e.currentTarget.src = placeholderImage;
                    }} />
            </div>
            ${book.price} <br />
            {book.publishDate.toString()}
        </div>
    )

}