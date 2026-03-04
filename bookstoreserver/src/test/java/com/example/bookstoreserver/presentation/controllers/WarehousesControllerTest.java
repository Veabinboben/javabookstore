package com.example.bookstoreserver.presentation.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.domain.services.WarehousesService;

@WebMvcTest(WarehousesController.class)
@AutoConfigureRestTestClient
public class WarehousesControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private WarehousesService warehousesService;

    @Test
    void testAllRequest() {
        List<Warehouse> expectedPage = List.of();

        when(warehousesService.getWarehouses(anyString()))
                .thenReturn(expectedPage);

        restTestClient.get().uri("/warehouses/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(expectedPage);
    }

}
