package com.example.bookstoreserver.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.Publisher;

public interface PublishersRepository extends JpaRepository<Publisher, Long> {
    public List<Publisher> findByNameContainingIgnoringCase(String name);
}
