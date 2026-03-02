package com.example.bookstoreserver.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Stock;
import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.data.repositories.StocksRepository;

@Service
public class StocksService {

    private final StocksRepository stocksRepository;

    public StocksService(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    public List<Stock> getStocksByBook(Book book) {
        return stocksRepository.findByBook(book);
    }

    public Stock getStockByBookAndWarehouse(Book book, Warehouse warehouse) {
        return stocksRepository.findByBookAndWarehouse(book, warehouse);
    }

    public void saveStock(Stock stock) {
        stocksRepository.save(stock);
    }

}
