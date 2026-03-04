package com.example.bookstoreserver.data.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Review;


public interface ReviewsRepository extends JpaRepository<Review,Long> {
    Page<Review> findByBook(Book book,Pageable pageable); 
}
