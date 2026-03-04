package com.example.bookstoreserver.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.bookstoreserver.data.entities.Review;
import com.example.bookstoreserver.data.repositories.ReviewsRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewsServiceTest {
    @Mock
    private ReviewsRepository reviewsRepository;

    @InjectMocks
    private ReviewsService reviewsService;

    @Test
    void testGetReviewsByBookPaginated() {
        int pageNum = 0;
        int pageSize = 3;
        Book book = new Book();
        Page<Review> expectedPage = Mockito.mock(Page.class);

        when(reviewsRepository.findByBook(eq(book), any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Review> result = reviewsService.getBookReviewsPaginated(pageNum, pageSize, book);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(reviewsRepository).findByBook(eq(book), eq(PageRequest.of(pageNum, pageSize)));
    }

    @Test
    void testSaveReview() {
        Review review = new Review();

        reviewsService.saveReview(review);

        verify(reviewsRepository, times(1)).save(review);
    }

}
