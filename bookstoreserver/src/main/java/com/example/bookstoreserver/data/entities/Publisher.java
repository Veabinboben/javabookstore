package com.example.bookstoreserver.data.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "publishers")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

}
