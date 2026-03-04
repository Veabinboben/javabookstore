package com.example.bookstoreserver.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.repositories.BooksRepository;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksService booksService;

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setTitle("test");
        when(booksRepository.findById(1l)).thenReturn(Optional.of(book));

        Book result = booksService.getBookById(1L);

        assertEquals(book, result);
        assertEquals(book.getTitle(), "test");

        verify(booksRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBooksPaginated() {
        int pageNum = 0;
        int pageSize = 3;
        String titleFilter = "spring";
        Page<Book> expectedPage = Mockito.mock(Page.class);

        when(booksRepository.findByTitleContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Book> result = booksService.getBooksPaginated(pageNum, pageSize, titleFilter);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(booksRepository).findByTitleContainingIgnoreCase(eq(titleFilter), eq(PageRequest.of(pageNum, pageSize)));
    }

    @Test
    void testSaveBook() {
        Book book = new Book();

        booksService.saveBook(book);

        verify(booksRepository, times(1)).save(book);
    }

    @Test
    void testDeleteBookById() {
        long id = 1;

        booksService.deleteBookById(id);

        verify(booksRepository, times(1)).deleteById(1l);
    }
}
