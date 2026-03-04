package com.example.bookstoreserver.data.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReviewTest {
    @Test
    public void testBookGettersAndSetters() {
        Review review = new Review();
        Book book = new Book();
        Author author = new Author();

        review.setId(1l);
        review.setContents("c");
        review.setRating(1);
        review.setBook(book);
        review.setAuthor(author);

        Assertions.assertEquals(1, review.getId());
        Assertions.assertEquals("c", review.getContents());
        Assertions.assertEquals(1, review.getRating());
        Assertions.assertEquals(book, review.getBook());
        Assertions.assertEquals(author, review.getAuthor());

    }
}
