package com.example.bookstoreserver.presentation.models.forms;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class BookForm {

    private MultipartFile file = null;

    private Long id = null;

    private String title;

    private Date publishDate;

    private Double price;

    private ArrayList<Long> authorIds = new ArrayList<>();

    private ArrayList<Long> publisherIds = new ArrayList<>();

    private ArrayList<Long> genreIds = new ArrayList<>();

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return this.price;
    };

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return this.publishDate;
    };

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public ArrayList<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(ArrayList<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public ArrayList<Long> getPublisherIds() {
        return publisherIds;
    }

    public void setPublisherIds(ArrayList<Long> publisherIds) {
        this.publisherIds = publisherIds;
    }

    public ArrayList<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Long> genreIds) {
        this.genreIds = genreIds;
    }

}
