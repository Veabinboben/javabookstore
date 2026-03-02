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
import com.example.bookstoreserver.data.entities.Genre;
import com.example.bookstoreserver.data.repositories.GenresRepository;

@ExtendWith(MockitoExtension.class)
public class GenresServiceTest {
    @Mock
    private GenresRepository genresRepository;

    @InjectMocks
    private GenresService genresService;

    @Test
    void testGetGenreById() {
        Genre genre = new Genre();
        when(genresRepository.findById(1l)).thenReturn(Optional.of(genre));

        Genre result = genresService.getGenreById(1L);

        assertEquals(genre, result);

        verify(genresRepository, times(1)).findById(1L);
    }

    @Test
    void testGetGenres() {

        List<Genre> expectedPage = List.of();

        when(genresRepository.findByNameContainingIgnoringCase(anyString()))
                .thenReturn(expectedPage);

        List<Genre> result = genresService.getGenres("all");

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(genresRepository).findByNameContainingIgnoringCase(eq("all"));
    }

    @Test
    void testSaveGenre() {
        Genre genre = new Genre();

        genresService.saveGenre(genre);

        verify(genresRepository, times(1)).save(genre);
    }

}
