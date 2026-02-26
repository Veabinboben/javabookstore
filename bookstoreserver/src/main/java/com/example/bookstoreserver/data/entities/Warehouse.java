package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "warehouses")
public class Warehouse {
        
    @Id
    @GeneratedValue
    private Long id;

    //TODO
}
