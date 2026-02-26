package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "cities")
public class City {
        
    @Id
    @GeneratedValue
    private Long id;   
    
    @Column(name = "name")
    public String name;
}
