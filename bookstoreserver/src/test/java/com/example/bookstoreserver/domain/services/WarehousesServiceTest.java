package com.example.bookstoreserver.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.data.repositories.WarehousesRepository;

@ExtendWith(MockitoExtension.class)
public class WarehousesServiceTest {
    @Mock
    private WarehousesRepository warehousesRepository;

    @InjectMocks
    private WarehousesService warehousesService;

    @Test
    void testGetAuthorById() {
        Warehouse warehouse = new Warehouse();
        when(warehousesRepository.findById(1l)).thenReturn(Optional.of(warehouse));

        Warehouse result = warehousesService.getWarehouseById(1L);

        assertEquals(warehouse, result);

        verify(warehousesRepository, times(1)).findById(1L);
    }

    @Test
    void testGetWarehouses() {

        List<Warehouse> expectedPage = List.of();

        when(warehousesRepository.findByAdressContainingIgnoringCase(anyString()))
                .thenReturn(expectedPage);

        List<Warehouse> result = warehousesService.getWarehouses("all");

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(warehousesRepository).findByAdressContainingIgnoringCase(eq("all"));
    }

    @Test
    void testSaveAuthor() {
        Warehouse warehouse = new Warehouse();

        warehousesService.saveWarehouse(warehouse);

        verify(warehousesRepository, times(1)).save(warehouse);
    }

}
