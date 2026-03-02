package com.example.bookstoreserver.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.entities.City;
import com.example.bookstoreserver.domain.services.CitiesService;

@RestController
@RequestMapping("/cities")
public class CitiesControlles {
    
    @Autowired
    private CitiesService citiesService;

     @GetMapping("/all")
    public ResponseEntity<List<City>> getCitites(
        @RequestParam(defaultValue = "") String nameFilter 
    ) {
        return ResponseEntity.ok(citiesService.getCities(nameFilter));
    }

}
