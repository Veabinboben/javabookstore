package com.example.bookstoreserver.domain.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Review;
import com.example.bookstoreserver.data.repositories.ReviewsRepository;

@Service
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;

    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    public Page<Review> getBookReviewsPaginated(int pageNum, int pagesize, Book book) {
        Pageable pageable = PageRequest.of(pageNum, pagesize);
        return reviewsRepository.findByBook(book, pageable);
    }

    public void saveReview(Review review) {
        reviewsRepository.save(review);
    }
}
