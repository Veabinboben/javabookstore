package com.example.bookstoreserver.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.entities.City;

public interface CitiesRepository extends JpaRepository<City,Long> {
    public List<City> findByNameContainingIgnoringCase(String name);
}
