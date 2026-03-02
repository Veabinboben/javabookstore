package com.example.bookstoreserver.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Genre;
import com.example.bookstoreserver.data.entities.Publisher;
import com.example.bookstoreserver.domain.services.GenresService;
import com.example.bookstoreserver.domain.services.PublishersService;

@RestController
@RequestMapping("/publishers")
public class PublishersController {
    
    @Autowired
    private PublishersService publishersService;

     @GetMapping("/all")
    public ResponseEntity<List<Publisher>> getPublishers(
        @RequestParam(defaultValue = "") String nameFilter 
    ) {
        return ResponseEntity.ok(publishersService.getPublishers(nameFilter));
    }

}