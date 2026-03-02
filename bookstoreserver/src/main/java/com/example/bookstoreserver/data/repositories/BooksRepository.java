package com.example.bookstoreserver.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstoreserver.data.entities.Book;

import jakarta.transaction.Transactional;

@Repository
public interface BooksRepository extends JpaRepository<Book,Long> {
    Page<Book> findByTitleContainingIgnoreCase(String titleFilter,Pageable pageable);
    // @Modifying
    // @Transactional 
    // @Query("UPDATE Book b SET b.name = :name WHERE p.id = :id")
    // int updateBook(@Param("name") String name,@Param("id") String id);

} 