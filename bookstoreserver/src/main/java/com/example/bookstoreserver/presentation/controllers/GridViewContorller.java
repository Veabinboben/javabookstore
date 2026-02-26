package com.example.bookstoreserver.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.BooksService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//TODO disable whitelabel error
//TODO add file upload support

@RestController
@RequestMapping("/main")
public class GridViewContorller {

    @Autowired
    private BooksService bookService;

    @GetMapping("/all")
    public ResponseEntity<Page<Book>> booksPaged(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize, 
        @RequestParam(defaultValue = "") String titleFilter 
    ) {
        //System.out.println(bookService.books().get(0).title);
        return ResponseEntity.ok(bookService.allBooksPaginated(page,pageSize,titleFilter));
    }
}   
