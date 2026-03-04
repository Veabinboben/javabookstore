package com.example.bookstoreserver.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.AuthorsService;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.domain.services.GenresService;
import com.example.bookstoreserver.domain.services.PublishersService;
import com.example.bookstoreserver.presentation.services.FileUploadService;

@WebMvcTest(BooksController.class)
@AutoConfigureRestTestClient
public class BooksControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private BooksService booksService;
    @MockitoBean
    private AuthorsService authorsService;
    @MockitoBean
    private GenresService genresService;
    @MockitoBean
    private PublishersService publisherService;
    @MockitoBean
    private FileUploadService fileUploadService;

    @Test
    void testAllRequest() {
        Page<Book> expectedPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(booksService.getBooksPaginated(eq(0), eq(10), eq("")))
                .thenReturn(expectedPage);

        restTestClient.get().uri("/books/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(expectedPage);
    }

    @Test
    void testGetByIdRequest() {
        Book book = new Book();

        when(booksService.getBookById(eq(1)))
                .thenReturn(book);

        restTestClient.get().uri("/books/getById?id=1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).equals(book);
    }

    @Test
    void testSaveRequest() {
        when(fileUploadService.uploadMultipart(any()))
                .thenReturn("");

        restTestClient.post()
                .uri("/books/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("price=19.99&title=test&publishDate=1111-11-11")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteByIdRequest() {

        restTestClient.post().uri("/books/delete?id=1")
                .exchange()
                .expectStatus().isOk();

        verify(booksService, times(1)).deleteBookById(eq(1l));

    }

}
