package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.Genre;

public interface GenresRepository extends JpaRepository<Genre,Long> {}
