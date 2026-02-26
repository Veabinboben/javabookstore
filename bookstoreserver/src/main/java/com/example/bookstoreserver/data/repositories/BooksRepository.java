package com.example.bookstoreserver.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstoreserver.data.entities.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book,Long> {
    Page<Book> findByTitleContainingIgnoreCase(String titleFilter,Pageable pageable);
} 