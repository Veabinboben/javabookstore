package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.Review;

public interface ReviewsRepository extends JpaRepository<Review,Long> {}
