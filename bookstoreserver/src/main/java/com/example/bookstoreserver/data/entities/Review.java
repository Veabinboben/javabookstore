package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name= "reviews")
public class Review {
        
    @Id
    @GeneratedValue
    private Long id;
}
