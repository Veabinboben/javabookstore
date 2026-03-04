package com.example.bookstoreserver.presentation.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.domain.services.WarehousesService;

@RestController
@RequestMapping("/warehouses")
public class WarehousesController {

    private final WarehousesService warehousesService;

    public WarehousesController(WarehousesService warehousesService) {
        this.warehousesService = warehousesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Warehouse>> getGenres(
            @RequestParam(defaultValue = "") String adressFilter) {
        return ResponseEntity.ok(warehousesService.getWarehouses(adressFilter));
    }

}