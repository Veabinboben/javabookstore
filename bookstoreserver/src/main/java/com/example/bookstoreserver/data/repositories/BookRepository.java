package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstoreserver.data.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {} 