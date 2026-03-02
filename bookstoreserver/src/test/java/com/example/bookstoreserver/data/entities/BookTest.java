package com.example.bookstoreserver.data.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookTest {
    @Test
    public void testBookGettersAndSetters() {
        Book book = new Book();
        Author author = new Author();
        Publisher publisher = new Publisher();
        Genre genre = new Genre();

        book.setId(1);
        book.setPrice(1.0);
        book.setTitle("title");
        book.setPublishDate(Date.valueOf("1111-11-11"));
        book.setCoverLink("link");
        book.addAuthor(author);
        book.addGenre(genre);
        book.addPublisher(publisher);

        Assertions.assertEquals(1, book.getId());
        Assertions.assertEquals(1.0, book.getPrice());
        Assertions.assertEquals("title", book.getTitle());
        Assertions.assertEquals(Date.valueOf("1111-11-11"), book.getPublishDate());
        Assertions.assertEquals("link", book.getCoverLink());
        Assertions.assertEquals(new HashSet<>(Set.of(author)), book.getAuthors());
        Assertions.assertEquals(new HashSet<>(Set.of(genre)), book.getGenres());
        Assertions.assertEquals(new HashSet<>(Set.of(publisher)), book.getPublishers());
    }
}
