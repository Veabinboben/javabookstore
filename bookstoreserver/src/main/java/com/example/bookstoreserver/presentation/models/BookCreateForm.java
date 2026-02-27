package com.example.bookstoreserver.presentation.models;

import org.springframework.web.multipart.MultipartFile;

public class BookCreateForm {
    private MultipartFile file;

    private String title;

    public MultipartFile getFile() { return file; }

    public void setFile(MultipartFile file) { this.file = file; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
