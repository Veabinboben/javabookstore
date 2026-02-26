package com.example.bookstoreserver.data.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Generated;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


//TODO maybe add getter\setters
@Entity(name="books")
public class Book {
    
    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "title")
    public String title;

    @Column(name = "publish_date")
    public Date publishDate;

    @Column(name = "price")
    public Double price;

    @Column(name = "cover_link")
    public String coverLink;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "book_authors", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "author_id") 
    )
    public Set<Author> authors = new HashSet<>();
   
    //TODO make it list of string, not of objects
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "book_genres", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "genre_id") 
    )
    public Set<Genre> genres = new HashSet<>();
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "book_publishers", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "publisher_id") 
    )
    public Set<Publisher> publishers = new HashSet<>();


}
