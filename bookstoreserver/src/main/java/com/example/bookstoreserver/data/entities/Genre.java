package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "genres")
public class Genre {
        
    @Id
    @GeneratedValue
    private Long id;

    public String name;
}
