package com.example.jdbc_test.ui;

import java.sql.Connection;
import java.sql.SQLException;

import com.example.jdbc_test.services.AuthorsDBService;
import com.example.jdbc_test.services.BooksDBService;
import com.example.jdbc_test.services.CitiesDBService;
import com.example.jdbc_test.services.GenresDBService;
import com.example.jdbc_test.services.PublishersDBService;
import com.example.jdbc_test.services.ReviewsDBService;
import com.example.jdbc_test.services.TransactionManager;
import com.example.jdbc_test.services.WarehouseDBService;
import com.example.jdbc_test.utils.InputUtils;

public class MainUI {
    
    private BooksDBService booksService;
    private AuthorsDBService authorsService;
    private WarehouseDBService warehouseService;
    private ReviewsDBService reviewsService;
    private PublishersDBService publishersService;
    private GenresDBService genresService;
    private CitiesDBService cityService;
    private TransactionManager transactionManager;

    private BooksUI booksUI;
    private AuthorsUI authorsUI;
    private ReviewsUI reviewsUI;
    private GenresUI genresUI;
    private CitiesUI citiesUI;
    private StocksUI stocksUI;

    public MainUI(Connection connection){

        this.booksService = new BooksDBService(connection);
        this.authorsService = new AuthorsDBService(connection);
        this.warehouseService = new WarehouseDBService(connection);
        this.reviewsService = new ReviewsDBService(connection);
        this.publishersService = new PublishersDBService(connection);
        this.genresService = new GenresDBService(connection);
        this.cityService = new CitiesDBService(connection);
        this.transactionManager = new TransactionManager(connection);

        this.booksUI = new BooksUI(booksService, authorsService, genresService, publishersService, transactionManager);
        this.authorsUI = new AuthorsUI(authorsService);
        this.reviewsUI = new ReviewsUI(booksService, authorsService, reviewsService, transactionManager,booksUI);
        this.genresUI = new GenresUI(genresService);
        this.citiesUI = new CitiesUI(cityService);
        this.stocksUI = new StocksUI(warehouseService, cityService, transactionManager,booksUI);
    };

    public void mainMenu() throws SQLException{
        while (true){
            System.out.println("""
                    Input menu item:
                    1. Add book.
                    2. Update book
                    3. Delete book
                    4. Stock warehouse.
                    5. Publish review
                    6. Show all books
                    7. Show all stocks
                    8. Show all authors
                    9. Show all reviews
                    10. Show all genres
                    11. Show all cities
                    other. Exit
                    """);
            int menuItem = InputUtils.inputInt();
            switch (menuItem) {
                case 1:
                    booksUI.insertBook();
                    break;
                case 2:
                    booksUI.updateBook();
                    break;
                case 3:
                    booksUI.deleteBook();
                    break;
                case 4:
                    stocksUI.addStock();
                    break;
                case 5:
                    reviewsUI.publishReview();
                    break;
                case 6:
                    booksUI.getBooks();
                    break;
                case 7:
                    stocksUI.getStocks();
                    break;
                case 8:
                    authorsUI.getAuthors();
                    break;
                case 9:
                    reviewsUI.getReviews();
                    break;
                case 10:
                    genresUI.getGenres();
                    break;
                case 11:
                    citiesUI.getCities();
                    break;
                default:
                    return;
            }
        }
    }
}
