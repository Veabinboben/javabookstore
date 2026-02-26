package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "publishers")
public class Publisher {
        
    @Id
    @GeneratedValue
    private Long id;
}
