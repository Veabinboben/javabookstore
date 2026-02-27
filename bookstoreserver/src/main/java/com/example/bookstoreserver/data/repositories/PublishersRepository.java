package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.Genre;
import com.example.bookstoreserver.data.entities.Publisher;

public interface PublishersRepository extends JpaRepository<Publisher,Long>{
    public Publisher findById(long id);

}
