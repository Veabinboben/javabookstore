package com.example.bookstoreserver.domain.services;

import java.util.List;
import com.example.bookstoreserver.data.repositories.WarehousesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Warehouse;

@Service
public class WarehousesService {

    @Autowired
    private  WarehousesRepository warehousesRepository;

    public List<Warehouse> getWarehouses(String adress){
        return warehousesRepository.findByAdressContainingIgnoringCase(adress);
    }

    public void saveWarehouse(Warehouse warehouse){
        warehousesRepository.save(warehouse);
    }

}
