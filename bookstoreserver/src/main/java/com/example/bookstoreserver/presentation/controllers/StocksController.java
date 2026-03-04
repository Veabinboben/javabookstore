package com.example.bookstoreserver.presentation.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Stock;
import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.domain.services.StocksService;
import com.example.bookstoreserver.domain.services.WarehousesService;
import com.example.bookstoreserver.presentation.models.ApiException;
import com.example.bookstoreserver.presentation.models.forms.StockFrom;

@RestController
@RequestMapping("stocks")
public class StocksController {

    private final StocksService stocksService;

    private final BooksService booksService;

    private final WarehousesService warehousesService;

    public StocksController(StocksService stocksService, BooksService booksService,
            WarehousesService warehousesService) {
        this.stocksService = stocksService;
        this.booksService = booksService;
        this.warehousesService = warehousesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Stock>> getStocks(
            @RequestParam(defaultValue = "-1") int bookId) throws ApiException {
        Book book;
        try {
            book = booksService.getBookById(bookId);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Invalid bookId");
        }
        return ResponseEntity.ok(stocksService.getStocksByBook(book));
    }

    @PostMapping("/save")
    public ResponseEntity<Stock> saveReview(@ModelAttribute StockFrom form) throws ApiException {
        Book book;
        Warehouse warehouse;
        try {
            book = booksService.getBookById(form.getBookId());
        } catch (NoSuchElementException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No book found");
        }
        try {
            warehouse = warehousesService.getWarehouseById(form.getWarehouseId());
        } catch (NoSuchElementException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No warehouse found");
        }
        try {
            Stock stock = stocksService.getStockByBookAndWarehouse(book, warehouse);
            if (stock != null) {
                stock.setAmmount(form.getStock());
            } else {
                stock = new Stock();
                stock.setAmmount(form.getStock());
                stock.setBook(book);
                stock.setWarehouse(warehouse);
            }
            stocksService.saveStock(stock);
            return ResponseEntity.ok(stock);
        } catch (NullPointerException e) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "Null value on not-null field");
        }
    }

}
