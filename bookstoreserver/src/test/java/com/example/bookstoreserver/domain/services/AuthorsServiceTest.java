package com.example.bookstoreserver.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.repositories.AuthorsRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorsServiceTest {
    @Mock
    private AuthorsRepository authorsRepository;

    @InjectMocks
    private AuthorsService authorsService;

    @Test
    void testGetAuthorById() {
        Author author = new Author();
        author.setName("test");
        when(authorsRepository.findById(1l)).thenReturn(Optional.of(author));

        Author result = authorsService.getAuthorById(1L);

        assertEquals(author, result);
        assertEquals(author.getName(), "test");

        verify(authorsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAuthors() {

        List<Author> expectedPage = List.of();

        when(authorsRepository.findByName(anyString()))
                .thenReturn(expectedPage);

        List<Author> result = authorsService.getAuthors("all");

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(authorsRepository).findByName(eq("all"));
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();

        authorsService.saveAuthor(author);

        verify(authorsRepository, times(1)).save(author);
    }

}
