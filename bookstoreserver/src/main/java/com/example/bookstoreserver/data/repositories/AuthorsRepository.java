package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstoreserver.data.entities.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author,Long> {
    public Author findById(long id);
} 