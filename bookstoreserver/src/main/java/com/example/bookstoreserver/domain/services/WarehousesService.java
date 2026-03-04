package com.example.bookstoreserver.domain.services;

import java.util.List;
import com.example.bookstoreserver.data.repositories.WarehousesRepository;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Warehouse;

@Service
public class WarehousesService {

    private final WarehousesRepository warehousesRepository;

    public WarehousesService(WarehousesRepository warehousesRepository) {
        this.warehousesRepository = warehousesRepository;
    }

    public List<Warehouse> getWarehouses(String adress) {
        return warehousesRepository.findByAdressContainingIgnoringCase(adress);
    }

    public Warehouse getWarehouseById(long id) {
        return warehousesRepository.findById(id).orElseThrow();
    }

    public void saveWarehouse(Warehouse warehouse) {
        warehousesRepository.save(warehouse);
    }

}
