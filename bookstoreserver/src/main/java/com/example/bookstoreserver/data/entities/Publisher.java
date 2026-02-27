package com.example.bookstoreserver.data.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String name;
    
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "publishers")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public String getName() {return this.name;}
   
    public String getDescription() {return this.description;}
   
    public Set<Book> getBooks() {return this.books;}

}
