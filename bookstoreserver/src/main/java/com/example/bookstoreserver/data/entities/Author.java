package com.example.bookstoreserver.data.entities;

import java.sql.Date;
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

    @Column(name = "middle_name")
    public String middleName;

    @Column(name = "surname")
    public String surname;

    @Column(name = "birthday")
    public Date birthday;
    
    @Column(name = "bio")
    public String bio;

    @Column(name = "photo_link")
    public String photoLink;



    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    
}
