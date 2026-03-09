package com.example.bookstoreserver.data.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenreTest {
    @Test
    public void testGenreGettersAndSetters() {
       
        Genre genre = new Genre();

        genre.setId(1l);

        Assertions.assertEquals(1, genre.getId());
    }
}
