package com.example.bookstoreserver.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Stock;
import com.example.bookstoreserver.data.entities.Warehouse;
import com.example.bookstoreserver.data.repositories.StocksRepository;

@ExtendWith(MockitoExtension.class)
public class StocksServiceTest {
    
    @Mock
    private StocksRepository stocksRepository;

    @InjectMocks
    private StocksService stocksService;


    @Test
    void getStocksbyBook() {
        Book book = new Book();
        List<Stock> expectedPage = List.of();

        when(stocksRepository.findByBook(eq(book)))
                .thenReturn(expectedPage);

        List<Stock> result = stocksService.getStocksByBook(book);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(stocksRepository).findByBook(eq(book));
    }

    @Test
    void getStockbyBookAndWarehouse() {
        Book book = new Book();
        Warehouse warehouse = new Warehouse();
        Stock expectedPage = new Stock();

        when(stocksRepository.findByBookAndWarehouse(eq(book),eq(warehouse)))
                .thenReturn(expectedPage);

        Stock result = stocksService.getStockByBookAndWarehouse(book,warehouse);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(stocksRepository).findByBookAndWarehouse(eq(book), eq(warehouse));
    }

    @Test
    void testSaveStock() {
        Stock stock = new Stock();

        stocksService.saveStock(stock);

        verify(stocksRepository, times(1)).save(stock);
    }

}
