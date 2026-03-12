import type { Book } from "../models/book";
import styles from "./Book.module.css";
import placeholderImage from '../../public/placeholder.png';

export function Book({ book, onClickHandler }: { book: Book, onClickHandler?: (id: number) => void }) {

    return (
        <div className={styles.container} onClick={() => onClickHandler?.(book.id)}>
            <div className={styles.title}>{book.title}</div>
            <div className={styles.row}>
                {book.authors != null && [...book.authors].length!=0 ? [...book.authors].map((author) => (
                    <div> {author.name}</div>
                )) : <div>No Author</div> }
            </div>
            <div key={book.id} className={styles.bookimage}>
                <div>
                    <img src={book.coverLink || placeholderImage} alt="no image :("
                    onError={(e) => {
                        e.currentTarget.onerror = null;
                        e.currentTarget.src = placeholderImage;
                    }} />
                </div>
            </div>
            
            <div>
            ${book.price} <br />
            {book.publishDate.toString()}
            </div>
        </div>
    )

}