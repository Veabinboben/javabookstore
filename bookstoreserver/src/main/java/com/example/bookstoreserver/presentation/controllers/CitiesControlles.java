package com.example.bookstoreserver.presentation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.City;
import com.example.bookstoreserver.domain.services.CitiesService;

@RestController
@RequestMapping("/cities")
public class CitiesControlles {

    private final CitiesService citiesService;

    public CitiesControlles(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<City>> getCitites(
            @RequestParam(defaultValue = "") String nameFilter) {
        return ResponseEntity.ok(citiesService.getCities(nameFilter));
    }

}
