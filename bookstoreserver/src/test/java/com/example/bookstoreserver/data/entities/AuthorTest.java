package com.example.bookstoreserver.data.entities;

import java.sql.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthorTest {
    @Test
    public void testAuthorGettersAndSetters() {

        Author author = new Author();

        author.setId(1l);
        author.setName("n");
        author.setMiddleName("m");
        author.setSurname("s");
        author.setBirthday(Date.valueOf("1111-11-11"));
        author.setPhotoLink("");
        author.setBio("b");

        Assertions.assertEquals(1l, author.getId());
        Assertions.assertEquals("n", author.getName());
        Assertions.assertEquals("m", author.getMiddleName());
        Assertions.assertEquals("s", author.getSurname());
        Assertions.assertEquals(Date.valueOf("1111-11-11"), author.getBirthday());
        Assertions.assertEquals("b", author.getBio());

    }
}
