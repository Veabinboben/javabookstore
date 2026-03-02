package com.example.bookstoreserver.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Stock;
import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.domain.services.StocksService;
import com.example.bookstoreserver.domain.services.WarehousesService;

@WebMvcTest(StocksController.class)
@AutoConfigureRestTestClient
public class StocksControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean    
    private StocksService stocksService;
    @MockitoBean
    private BooksService booksService;
    @MockitoBean
    private WarehousesService warehousesService;

    @Test
    void testAllRequest() {
        List<Stock> expectedPage = List.of();

        when(booksService.getBookById(eq(0)))
                .thenReturn(new Book());

        when(stocksService.getStocksByBook(any()))
                .thenReturn(expectedPage);

        

        restTestClient.get().uri("/stocks/all?id=0")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(expectedPage);
    }

    @Test
    void testSaveRequest() {

        when(booksService.getBookById(eq(1)))
                .thenReturn(new Book());

        when(warehousesService.getWarehouseById(eq(1)))
                .thenReturn(new Warehouse());

        restTestClient.post()
                .uri("/stocks/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("stock=1&bookId=1&warehouseId=1")
                .exchange()
                .expectStatus().isOk();
    }

}
