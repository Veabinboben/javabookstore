package com.example.bookstoreserver.data.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private Date birthday;
    
    @Column(name = "bio")
    private String bio;

    @Column(name = "photo_link")
    private String photoLink;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public String getNmae() { return this.name; };

    public String getMiddleName() { return this.middleName; };
    
    public String getSurname() { return this.surname; };
    
    public Date getBirthday() { return this.birthday; };
    
    public String getBio() { return this.bio; };
    
    public String getPhotoLink() { return this.photoLink; };
    
    public Set<Book> getBooks() { return this.books; };

    
}
