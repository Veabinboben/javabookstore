package com.example.bookstoreserver.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstoreserver.data.entities.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author,Long> {
    @Query("SELECT a FROM authors a WHERE a.name like %:name% or a.middleName like %:name% or a.surname like %:name%")
    public List<Author> findByName(@Param("name") String name);
} 