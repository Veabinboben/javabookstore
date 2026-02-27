package com.example.bookstoreserver.presentation.models;

import java.awt.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;


//TODO add validation
public class BookForm {

    private MultipartFile file = null;

    private String title = null;
    
    private Date publishDate = null;
    
    private Double price = null;
    
    private ArrayList<Long> authorIds = new ArrayList<>();;
    
    private ArrayList<Long> publisherIds = new ArrayList<>();;
    
    private ArrayList<Long> genreIds = new ArrayList<>();;


    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) { this.file = file; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return this.price; };
    public void setPrice(double price) {this.price = price;}

    public Date getPublishDate() { return this.publishDate; };
    public void setPublishDate(Date publishDate) {this.publishDate = publishDate;}

    public ArrayList<Long> getAuthorIds() {return authorIds;}
    public void setAuthorIds(ArrayList<Long> authorIds) {this.authorIds = authorIds;}

    public ArrayList<Long> getPublisherIds() {return publisherIds;}
    public void setPublisherIds(ArrayList<Long> publisherIds) {this.publisherIds = publisherIds;}

    public ArrayList<Long> getGenreIds() {return genreIds;}
    public void setGenreIds(ArrayList<Long> genreIds) {this.genreIds = genreIds;}


}
