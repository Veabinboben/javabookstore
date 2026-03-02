package com.example.bookstoreserver.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Genre;
import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.domain.services.GenresService;
import com.example.bookstoreserver.domain.services.WarehousesService;

@RestController
@RequestMapping("/warehouses")
public class WarehousesController {
    
    @Autowired
    private WarehousesService warehousesService;

     @GetMapping("/all")
    public ResponseEntity<List<Warehouse>> getGenres(
        @RequestParam(defaultValue = "") String adressFilter 
    ) {
        return ResponseEntity.ok(warehousesService.getWarehouses(adressFilter));
    }

}