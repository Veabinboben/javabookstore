package com.example.bookstoreserver.data.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "publishers")
public class Publisher {       
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    public String name;
    
    @Column(name = "description")
    public String description;

    @ManyToMany(mappedBy = "publishers")
    private Set<Book> books = new HashSet<>();
}
