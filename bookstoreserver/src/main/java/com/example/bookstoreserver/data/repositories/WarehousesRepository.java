package com.example.bookstoreserver.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstoreserver.data.entities.Warehouse;
import java.util.List;


public interface WarehousesRepository extends JpaRepository<Warehouse,Long> {
    List<Warehouse> findByAdressContainingIgnoringCase(String adress);
    
}
