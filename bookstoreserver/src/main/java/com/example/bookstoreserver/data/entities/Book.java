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
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "publish_date")
    private Date publishDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "cover_link")
    private String coverLink;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "book_authors", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "author_id") 
    )
    private Set<Author> authors = new HashSet<>();
   
    //TODO make it list of string, not of objects
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "book_genres", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "genre_id") 
    )
    private Set<Genre> genres = new HashSet<>();
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "book_publishers", 
        joinColumns = @JoinColumn(name = "book_id"), 
        inverseJoinColumns = @JoinColumn(name = "publisher_id") 
    )
    private Set<Publisher> publishers = new HashSet<>();

    public String getTitle() { return this.title; };
    public void setTitle(String title) {this.title = title;}
    
    public Date getPublishDate() { return this.publishDate; };
    public void setPublishDate(Date publishDate) {this.publishDate = publishDate;}

    public double getPrice() { return this.price; };
    public void setPrice(double price) {this.price = price;}

    public String getCoverLink() { return this.coverLink; };
    public void setCoverLink(String coverLink) {this.coverLink = coverLink;}
    
    public Set<Author> getAuthors() { return this.authors; };
    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public Set<Genre> getGenres() { return this.genres; };
    public void addGenre(Genre  genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }
    
    public Set<Publisher> getPublishers() { return this.publishers; };
    public void addPublisher(Publisher  publisher) {
        this.publishers.add(publisher);
        publisher.getBooks().add(this);
    }

    

}
