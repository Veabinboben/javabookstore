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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity(name = "authors")
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(name = "middle_name")
    @NotNull
    @NotEmpty(message = "Middle Name cannot be empty")
    private String middleName;

    @Column(name = "surname")
    @NotNull
    @NotEmpty(message = "Surname cannot be empty")
    private String surname;

    @Column(name = "birthday")
    @NotNull
    @NotEmpty(message = "Date cannot be empty")
    private Date birthday;

    @Column(name = "bio")
    @NotNull
    @NotEmpty(message = "BIO cannot be empty")
    private String bio;

    @Column(name = "photo_link")
    private String photoLink;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhotoLink() {
        return this.photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

}
