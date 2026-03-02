package com.example.bookstoreserver.presentation.controllers;

import static org.mockito.ArgumentMatchers.eq;
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

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Review;
import com.example.bookstoreserver.domain.services.AuthorsService;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.domain.services.ReviewsService;

@WebMvcTest(ReviewsController.class)
@AutoConfigureRestTestClient
public class ReviewsControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private BooksService bookService;
    @MockitoBean
    private AuthorsService authorsService;
    @MockitoBean
    private ReviewsService reviewsService;

    @Test
    void testAllRequest() {
        Book book = new Book();
        Page<Review> expectedPage = new PageImpl<>(List.of(), PageRequest.of(0, 10), 0);

        when(reviewsService.getBookReviewsPaginated(eq(0), eq(10), eq(book)))
                .thenReturn(expectedPage);

        restTestClient.get().uri("/reviews/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(expectedPage);
    }

    @Test
    void testSaveRequest() {

        when(bookService.getBookById(eq(1)))
                .thenReturn(new Book());
        when(authorsService.getAuthorById(eq(1)))
                .thenReturn(new Author());

        restTestClient.post()
                .uri("/reviews/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("content=c&rating=1&bookId=1&authorId=1")
                .exchange()
                .expectStatus().isOk();
    }

}
