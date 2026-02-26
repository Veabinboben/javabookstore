package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.Warehouse;

public interface WarehousesRepository extends JpaRepository<Warehouse,Long> {}
