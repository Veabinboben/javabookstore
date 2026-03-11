package com.example.bookstoreserver.presentation.models.forms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BookForm {

    private MultipartFile file = null;

    private Long id = null;

    private String title;

    private String imageUrl = null;

    private LocalDate publishDate;

    private Double price;

    private List<Long> authorIds = new ArrayList<>();

    private List<Long> publisherIds = new ArrayList<>();

    private List<Long> genreIds = new ArrayList<>();

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public List<Long> getPublisherIds() {
        return publisherIds;
    }

    public void setPublisherIds(List<Long> publisherIds) {
        this.publisherIds = publisherIds;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

}
