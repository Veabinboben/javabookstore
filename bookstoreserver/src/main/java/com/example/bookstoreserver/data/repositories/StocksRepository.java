package com.example.bookstoreserver.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Review;
import com.example.bookstoreserver.data.entities.Stock;
import com.example.bookstoreserver.data.entities.Warehouse;

public interface StocksRepository extends JpaRepository<Stock,Long> {
    List<Stock> findByBook(Book book); 

    Stock findByBookAndWarehouse(Book book, Warehouse warehouse);
}