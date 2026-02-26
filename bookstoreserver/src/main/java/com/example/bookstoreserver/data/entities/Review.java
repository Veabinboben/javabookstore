package com.example.bookstoreserver.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity(name= "reviews")
public class Review {
        
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "contents")
    public String contents;

    @Column(name = "rating")
    //TODO add validation
    public Integer rating;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "author_id", nullable = false) 
    private Author author;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "book_id", nullable = false) 
    private Book book;
}
