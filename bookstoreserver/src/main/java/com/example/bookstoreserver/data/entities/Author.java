package com.example.bookstoreserver.data.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "authors")
public class Author {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    public String name;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

}
