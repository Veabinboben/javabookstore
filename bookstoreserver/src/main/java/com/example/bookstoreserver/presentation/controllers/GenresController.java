package com.example.bookstoreserver.presentation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Genre;
import com.example.bookstoreserver.domain.services.GenresService;

@RestController
@RequestMapping("/genres")
public class GenresController {

    private final GenresService genresService;

    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Genre>> getGenres(
            @RequestParam(defaultValue = "") String nameFilter) {
        return ResponseEntity.ok(genresService.getGenres(nameFilter));
    }

}