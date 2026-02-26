package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.City;

public interface CitiesRepository extends JpaRepository<City,Long> {}
