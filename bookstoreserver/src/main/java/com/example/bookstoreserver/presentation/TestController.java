package com.example.bookstoreserver.presentation;

import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.BookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TestController {

    @Autowired
    private BookService bookService;

    @GetMapping("/test")
    public String getMethodName(@RequestParam(defaultValue = "no") String param) {
        return "Hello world!";
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Book>> books(@RequestParam(defaultValue = "none") String param) {
        //System.out.println(bookService.books().get(0).title);
        return ResponseEntity.ok(bookService.books());
    }
    
}   
